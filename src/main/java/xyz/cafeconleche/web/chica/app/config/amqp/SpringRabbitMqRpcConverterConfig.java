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

import xyz.cafeconleche.web.chica.amqp.listeners.CustomMessageDelegate;


@Configuration
public class SpringRabbitMqRpcConverterConfig {

	private static final String RPC_REPLY_QUEUE_NAME = "rpc.reply.queue.name";
	private static final String RPC_REQ_QUEUE_NAME = "rpc.req.queue.name";
	private static final String RPC_DIRECT_EXCHANGE_REQ_NAME = "rpc.direct.exchange.req.name";
	private static final String RPC_DIRECT_EXCHANGE_REPLY_NAME = "rpc.direct.exchange.reply.name";
	private static final String RPC_ROUTING_TEST_NAME = "rpc.routing.test.name";
	
	@Bean
	@Qualifier("rpcReplyQueue")
	public Queue rpcReplyQueue() {
		return new Queue(RPC_REPLY_QUEUE_NAME);
	}
	
	@Bean
	@Qualifier("rpcRequestQueue")
	public Queue rpcRequestQueue() {
		return new Queue(RPC_REQ_QUEUE_NAME);
	}
	
	@Bean
	public DirectExchange rpcDirectExchange() {
		return new DirectExchange(RPC_DIRECT_EXCHANGE_REQ_NAME);
	}
	
	@Bean
	public DirectExchange rpcReplyExchange() {
		return new DirectExchange(RPC_DIRECT_EXCHANGE_REPLY_NAME);
	}
	
	@Bean
	public Binding rpcReplyBinding() {
		return BindingBuilder.bind(rpcReplyQueue()).to(rpcReplyExchange()).with(RPC_ROUTING_TEST_NAME);
	}

	@Bean
	public Binding rpcBinding() {
		return BindingBuilder.bind(rpcRequestQueue()).to(rpcDirectExchange()).with(RPC_ROUTING_TEST_NAME);
	}
	
//	@Bean 
//	public MessageListenerAdapter simpleMEssageListenerAdapter() {
//		return new PojoMessageListener();
//	}

	@Bean
	public MessageListenerAdapter messageListenerAdapter(MessageConverter jsonMessageConverter) {
		MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(new CustomMessageDelegate(), jsonMessageConverter);
		listenerAdapter.setDefaultListenerMethod("handleMezzage");
		return listenerAdapter;
	}
	
	@Bean
	public SimpleMessageListenerContainer rpcServiceListenerContainer(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
		System.out.println("--- rpcServiceListenerContainer ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(rpcRequestQueue());
        listenerContainer.setMessageListener(messageListenerAdapter(jsonMessageConverter));
        return listenerContainer;
	}
	
	@Bean
	public SimpleMessageListenerContainer rpcReplyListenerContainer(RabbitTemplate rabbitTemplate, ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
		System.out.println("--- rpcReplyListenerContainer ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(rpcReplyQueue());
        listenerContainer.setMessageListener(rabbitTemplate);
        return listenerContainer;
	}
	
	/**
	 * Listener that upcases the request.
	 */
//	public static class PojoListener2 {
//
//		public String handleMessage(String foo) {
//			System.out.println("== handleMessage ==");
//			System.out.println("foo: " + foo);
//			return foo.toUpperCase();
//		}
//	}
	
	
	/*
	@Bean
	public ProducerRpcService producerRpcService() {
		ProducerRpcService producerRpcService = new ProducerRpcServiceRabbitMqImpl();
		return producerRpcService;
	}

	
	@Bean
    public SimpleMessageListenerContainer listenerContainerRpc(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    	System.out.println("--- listenerContainer RPC queue ---");
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(replyQueue());
        listenerContainer.setMessageConverter(jsonMessageConverter);
        listenerContainer.setMessageListener(producerRpcService());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainer;
    }
    */
	
}
