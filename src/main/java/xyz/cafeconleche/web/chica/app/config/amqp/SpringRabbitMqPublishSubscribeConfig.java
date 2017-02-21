package xyz.cafeconleche.web.chica.app.config.amqp;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.cafeconleche.web.chica.service.consumer.ConsumerPublishSubscribeService;
import xyz.cafeconleche.web.chica.service.consumer.impl.ConsumerPublishSubscribeServiceRabbitMqImpl;

@Configuration
public class SpringRabbitMqPublishSubscribeConfig {

	private static final String PUBLISH_SUBSCRIBE_A_QUEUE_NAME = "publish.subscribe.a.queue.name";
	private static final String PUBLISH_SUBSCRIBE_B_QUEUE_NAME = "publish.subscribe.b.queue.name";
	private static final String FANOUT_EXCHANGE_NAME = "publish.subscribe.exchange.name";
	
	@Bean
	@Qualifier("publishSubscribeAQueue")
	public Queue publishSubscribeAQueue() {
		System.out.println("--- publishSubscribeAQueue ---");
		return new Queue(PUBLISH_SUBSCRIBE_A_QUEUE_NAME);
	}
	
	@Bean
	@Qualifier("publishSubscribeBQueue")
	public Queue publishSubscribeBQueue() {
		System.out.println("--- publishSubscribeBQueue ---");
		return new Queue(PUBLISH_SUBSCRIBE_B_QUEUE_NAME);
	}
	
	@Bean
	public FanoutExchange exchange() {
		return new FanoutExchange(FANOUT_EXCHANGE_NAME);
	}
	
	@Bean
	public Binding bindingA() {
		return BindingBuilder.bind(publishSubscribeAQueue()).to(exchange());
	}
	
	@Bean
	public Binding bindingB() {
		return BindingBuilder.bind(publishSubscribeBQueue()).to(exchange());
	}
	
	@Bean
	public ConsumerPublishSubscribeService consumerPublishSubscribeServiceA() {
		ConsumerPublishSubscribeService consumerPublishSubscribeService = new ConsumerPublishSubscribeServiceRabbitMqImpl();
		consumerPublishSubscribeService.setConsumerId("PUB/SUB CONSUMER A");
		return consumerPublishSubscribeService;
	}
	
	@Bean
	public ConsumerPublishSubscribeService consumerPublishSubscribeServiceB() {
		ConsumerPublishSubscribeService consumerPublishSubscribeService = new ConsumerPublishSubscribeServiceRabbitMqImpl();
		consumerPublishSubscribeService.setConsumerId("PUB/SUB CONSUMER B");
		return consumerPublishSubscribeService;
	}

	@Bean
    public SimpleMessageListenerContainer publishSubscribeListenerContainerA(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    	System.out.println("--- listenerContainer work queue A ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(publishSubscribeAQueue());
        listenerContainer.setMessageConverter(jsonMessageConverter);
        listenerContainer.setMessageListener(consumerPublishSubscribeServiceA());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainer;
    }
	
	@Bean
	public SimpleMessageListenerContainer publishSubscribeListenerContainerB(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    	System.out.println("--- listenerContainer work queue B ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(publishSubscribeBQueue());
        listenerContainer.setMessageConverter(jsonMessageConverter);
        listenerContainer.setMessageListener(consumerPublishSubscribeServiceB());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainer;
    }
	
}
