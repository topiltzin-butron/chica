package xyz.cafeconleche.web.chica.app.config.amqp;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.cafeconleche.web.chica.service.consumer.ConsumerService;
import xyz.cafeconleche.web.chica.service.consumer.impl.ConsumerServiceRabbitMqImpl;
import xyz.cafeconleche.web.chica.service.producer.ProducerService;
import xyz.cafeconleche.web.chica.service.producer.impl.ProducerServiceRabbitMqImpl;


@Configuration
public class SpringRabbitMqConfig {

	private static final String SIMPLE_MESSAGE_QUEUE = "simple.queue.name";

    @Bean
    public ConnectionFactory connectionFactory() {
    	System.out.println("--- connectionFactory ---");
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public Queue simpleQueue() {
    	System.out.println("--- simpleQueue ---");
        return new Queue(SIMPLE_MESSAGE_QUEUE);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
    	System.out.println("--- jsonMessageConverter ---");
        return new JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
    	System.out.println("--- rabbitTemplate ---");
    	
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
//        template.setRoutingKey(SIMPLE_MESSAGE_QUEUE);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
    
    @Bean
	public ProducerService producerService() {
    	return new ProducerServiceRabbitMqImpl();
    }
    
    @Bean
    public ConsumerService consumerService() {
    	return new ConsumerServiceRabbitMqImpl();
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainer() {
    	System.out.println("--- listenerContainer ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory());
        listenerContainer.setQueues(simpleQueue());
        listenerContainer.setMessageConverter(jsonMessageConverter());
        listenerContainer.setMessageListener(consumerService());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainer;
    }
	
}
