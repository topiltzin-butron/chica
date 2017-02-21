package xyz.cafeconleche.web.chica.app.config.amqp;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.cafeconleche.web.chica.service.consumer.ConsumerWorkQueueService;
import xyz.cafeconleche.web.chica.service.consumer.impl.ConsumerWorkQueueServiceRabbitMqImpl;
import xyz.cafeconleche.web.chica.service.producer.ProducerWorkQueueService;
import xyz.cafeconleche.web.chica.service.producer.impl.ProducerWorkQueueServiceRabbitMqImpl;

@Configuration
public class SpringRabbitMqWorkQueuesConfig {

	private static final String WORK_QUEUE_NAME= "work.queue.name";

	@Bean
	@Qualifier("workQueue")
	public Queue workQueue() {
		System.out.println("--- workQueue ---");
		return new Queue(WORK_QUEUE_NAME);
	}
	
	@Bean
	public ProducerWorkQueueService producerWorkQueueService() {
    	return new ProducerWorkQueueServiceRabbitMqImpl();
    }
	
	@Bean
	public ConsumerWorkQueueService consumerWorkQueueServiceA() {
		ConsumerWorkQueueService consumerWorkQueueService = new ConsumerWorkQueueServiceRabbitMqImpl();
		consumerWorkQueueService.setConsumerId("CONSUMER A");
		return consumerWorkQueueService;
	}
	
	@Bean
	public ConsumerWorkQueueService consumerWorkQueueServiceB() {
		ConsumerWorkQueueService consumerWorkQueueService = new ConsumerWorkQueueServiceRabbitMqImpl();
		consumerWorkQueueService.setConsumerId("CONSUMER B");
		return consumerWorkQueueService;
	}
	
	@Bean
    public SimpleMessageListenerContainer listenerContainerA(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    	System.out.println("--- listenerContainer work queue A ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(workQueue());
        listenerContainer.setMessageConverter(jsonMessageConverter);
        listenerContainer.setMessageListener(consumerWorkQueueServiceA());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainer;
    }
	
	@Bean
    public SimpleMessageListenerContainer listenerContainerB(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    	System.out.println("--- listenerContainer work queue B ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(workQueue());
        listenerContainer.setMessageConverter(jsonMessageConverter);
        listenerContainer.setMessageListener(consumerWorkQueueServiceB());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        
        return listenerContainer;
    }

}
