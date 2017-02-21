package xyz.cafeconleche.web.chica.app.config.amqp;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.cafeconleche.web.chica.service.consumer.ConsumerTopicService;
import xyz.cafeconleche.web.chica.service.consumer.impl.ConsumerTopicServiceRabbitMqImpl;


@Configuration
public class SpringRabbitMqTopicConfig {

	private static final String TOPIC_EXCHANGE_NAME = "topic.exchange.name";
	private static final String TOPIC_BINDING_1_NAME = "*.orange.*";
	private static final String TOPIC_BINDING_2_NAME = "*.*.rabbit";
	private static final String TOPIC_BINDING_3_NAME = "lazy.#";
	private static final String TOPIC_QUEUE_A_NAME = "topic.queue.a.name";
	private static final String TOPIC_QUEUE_B_NAME = "topic.queue.b.name";

	@Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
		System.out.println("--- AmqpAdmin ---");
        return new RabbitAdmin(connectionFactory);
    }
	
	@Bean
	@Qualifier("topicQueueA")
	public Queue topicQueueA() {
		System.out.println("--- topicQueueA ---");
		return new Queue(TOPIC_QUEUE_A_NAME);
	}

	@Bean
	@Qualifier("topicQueueB")
	public Queue topicQueueB() {
		System.out.println("--- topicQueueB ---");
		return new Queue(TOPIC_QUEUE_B_NAME);
	}
	
	@Bean TopicExchange topicExchange() {
		return new TopicExchange(TOPIC_EXCHANGE_NAME);
	}
	
	@Bean
	public Binding bindingTopicA() {
		return BindingBuilder.bind(topicQueueA()).to(topicExchange()).with(TOPIC_BINDING_1_NAME);
	}
	
	@Bean
	public Binding bindingTopicB() {
		return BindingBuilder.bind(topicQueueB()).to(topicExchange()).with(TOPIC_BINDING_2_NAME);
	}
	
	@Bean
	public Binding bindingTopicC() {
		return BindingBuilder.bind(topicQueueB()).to(topicExchange()).with(TOPIC_BINDING_3_NAME);
	}
	
	@Bean
	public ConsumerTopicService consumerTopicServiceA() {
		ConsumerTopicService consumerTopicService = new ConsumerTopicServiceRabbitMqImpl();
		consumerTopicService.setConsumerId("CONSUMER-TOPIC-A");
		return consumerTopicService;
	}
	
	@Bean
	public ConsumerTopicService consumerTopicServiceB() {
		ConsumerTopicService consumerTopicService = new ConsumerTopicServiceRabbitMqImpl();
		consumerTopicService.setConsumerId("CONSUMER-TOPIC-B");
		return consumerTopicService;
	}
	
	@Bean
    public SimpleMessageListenerContainer listenerContainerError(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    	System.out.println("--- listenerContainer routing queue ERROR ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(topicQueueA());
        listenerContainer.setMessageConverter(jsonMessageConverter);
        listenerContainer.setMessageListener(consumerTopicServiceA());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainer;
    }
	
	@Bean
    public SimpleMessageListenerContainer listenerContainerLog(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    	System.out.println("--- listenerContainer routing queue LOG ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(topicQueueB());
        listenerContainer.setMessageConverter(jsonMessageConverter);
        listenerContainer.setMessageListener(consumerTopicServiceB());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainer;
    }
	
}
