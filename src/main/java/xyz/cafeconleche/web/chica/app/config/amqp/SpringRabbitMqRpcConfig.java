package xyz.cafeconleche.web.chica.app.config.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringRabbitMqRpcConfig {

	private static final String REPLY_QUEUE_NAME = "reply.queue.name";
	private static final String REQ_QUEUE_NAME = "req.queue.name";
	private static final String DIRECT_EXCHANGE_REQ_NAME = "direct.exchange.req.name";
	private static final String DIRECT_EXCHANGE_REPLY_NAME = "direct.exchange.reply.name";
	private static final String ROUTING_TEST_NAME = "routing.test.name";
	
	@Bean
	@Qualifier("replyQueue")
	public Queue replyQueue() {
		System.out.println("--- replyQueue ---");
		return new Queue(REPLY_QUEUE_NAME);
	}
	
	@Bean
	@Qualifier("requestQueue")
	public Queue requestQueue() {
		System.out.println("--- requestQueue ---");
		return new Queue(REQ_QUEUE_NAME);
	}
	
	@Bean
	public DirectExchange ex() {
		return new DirectExchange(DIRECT_EXCHANGE_REQ_NAME);
	}
	
	@Bean
	public DirectExchange replyExchange() {
		return new DirectExchange(DIRECT_EXCHANGE_REPLY_NAME);
	}
	
	@Bean
	public Binding replyBinding() {
		return BindingBuilder.bind(replyQueue()).to(replyExchange()).with(ROUTING_TEST_NAME);
	}

	@Bean
	public Binding binding() {
		return BindingBuilder.bind(requestQueue()).to(ex()).with(ROUTING_TEST_NAME);
	}
	
	@Bean
	public SimpleMessageListenerContainer serviceListenerContainer(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
		System.out.println("--- serviceListenerContainer ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(requestQueue());
        listenerContainer.setMessageListener(new MessageListenerAdapter(new PojoListener()));
        return listenerContainer;
	}
	
	@Bean
	public SimpleMessageListenerContainer replyListenerContainer(RabbitTemplate rabbitTemplate, ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
		System.out.println("--- serviceListenerContainer ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(replyQueue());
        listenerContainer.setMessageListener(rabbitTemplate);
        return listenerContainer;
	}
	
	/**
	 * Listener that upcases the request.
	 */
	public static class PojoListener {

		public String handleMessage(String foo) {
			System.out.println("== handleMessage ==");
			System.out.println("foo: " + foo);
			return foo.toUpperCase();
		}
	}
	
}
