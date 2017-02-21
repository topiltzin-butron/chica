package xyz.cafeconleche.web.chica.app.config.amqp;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.cafeconleche.web.chica.service.consumer.ConsumerRoutingService;
import xyz.cafeconleche.web.chica.service.consumer.impl.ConsumerRoutingServiceRabbitMqImpl;


@Configuration
public class SpringRabbitMqRoutingConfig {

	private static final String ROUTING_EXCHANGE_NAME = "routing.exchange.name";
	private static final String ERROR_BINDING_NAME = "error.binding.name";
	private static final String INFO_BINDING_NAME = "info.binding.name";
	private static final String WARN_BINDING_NAME = "warn.binding.name";
	
	private static final String ERROR_QUEUE_NAME = "error.queue.name";
	private static final String LOG_QUEUE_NAME = "logs.queue.name";

	
	@Bean
	@Qualifier("errorQueue")
	public Queue errorQueue() {
		System.out.println("--- errorQueue ---");
		return new Queue(ERROR_QUEUE_NAME);
	}
	
	@Bean
	@Qualifier("logQueue")
	public Queue logQueue() {
		System.out.println("--- logQueue ---");
		return new Queue(LOG_QUEUE_NAME);
	}
	
	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(ROUTING_EXCHANGE_NAME);
	}
	
	@Bean
	public Binding bindingErrorA() {
		return BindingBuilder.bind(errorQueue()).to(directExchange()).with(ERROR_BINDING_NAME);
	}
	
	@Bean
	public Binding bindingErrorB() {
		return BindingBuilder.bind(logQueue()).to(directExchange()).with(ERROR_BINDING_NAME);
	}

	@Bean
	public Binding bindingInfo() {
		return BindingBuilder.bind(logQueue()).to(directExchange()).with(INFO_BINDING_NAME);
	}
	
	@Bean
	public Binding bindingWarn() {
		return BindingBuilder.bind(logQueue()).to(directExchange()).with(WARN_BINDING_NAME);
	}
	
	@Bean
	public ConsumerRoutingService consumerRoutingServiceError() {
		ConsumerRoutingService consumerRoutingService = new ConsumerRoutingServiceRabbitMqImpl();
		consumerRoutingService.setConsumerId("CONSUMER-ROUTING-ERROR");
		return consumerRoutingService;
	}
	
	@Bean
	public ConsumerRoutingService consumerRoutingServiceLog() {
		ConsumerRoutingService consumerRoutingService = new ConsumerRoutingServiceRabbitMqImpl();
		consumerRoutingService.setConsumerId("CONSUMER-ROUTING-LOG");
		return consumerRoutingService;
	}
	
	@Bean
    public SimpleMessageListenerContainer listenerContainerError(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    	System.out.println("--- listenerContainer routing queue ERROR ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(errorQueue());
        listenerContainer.setMessageConverter(jsonMessageConverter);
        listenerContainer.setMessageListener(consumerRoutingServiceError());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainer;
    }
	
	@Bean
    public SimpleMessageListenerContainer listenerContainerLog(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    	System.out.println("--- listenerContainer routing queue LOG ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(errorQueue(), logQueue());
        listenerContainer.setMessageConverter(jsonMessageConverter);
        listenerContainer.setMessageListener(consumerRoutingServiceLog());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainer;
    }
	
}
