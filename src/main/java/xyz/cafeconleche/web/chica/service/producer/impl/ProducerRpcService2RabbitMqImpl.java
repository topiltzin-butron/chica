package xyz.cafeconleche.web.chica.service.producer.impl;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.dto.CustomMessage;
import xyz.cafeconleche.web.chica.service.producer.ProducerRpc2Service;

@Service("rpc2Service")
public class ProducerRpcService2RabbitMqImpl implements ProducerRpc2Service {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private DirectExchange rpcDirectExchange;
	@Autowired
	private DirectExchange rpcReplyExchange;
	
	@Override
	public void produce() {

		String routing = "rpc.routing.test.name";
		System.out.println("produce rpc 2");
		System.out.println("reply address: " + rpcReplyExchange.getName() + "/" + routing);
		

		rabbitTemplate.setExchange(rpcDirectExchange.getName());
		rabbitTemplate.setRoutingKey(routing);
		rabbitTemplate.setReplyAddress(rpcReplyExchange.getName() + "/" + routing);
//		rabbitTemplate.setReplyAddress(replyExchange.getName() + "/" + replyQueue.getName());
		
		String text = "{id=7, name='RabbitMQ Spring JSON Example RPC'}";
		Message message = MessageBuilder.withBody(text.getBytes())
				.setContentType("text/plain")
//				.setContentType("application/json")
				.build();
		
//		Message reply = rabbitTemplate.sendAndReceive(message);
		
		CustomMessage customMessage = new CustomMessage(88, "The crazy 88");
//		Object reply = rabbitTemplate.convertSendAndReceive(customMessage);
		
		CustomMessage reply = (CustomMessage) rabbitTemplate.convertSendAndReceive(customMessage);
		
		System.out.println("222 REPLYPLEYREPLYREPLYREPLYREPLYREPLY");
		System.out.println("class: " + reply.getClass());
		System.out.println(reply.getName());
		System.out.println("222 REPLYPLEYREPLYREPLYREPLYREPLYREPLY");
		
//		System.out.println("222 BODYBODYBODYBODYBODYBODYBODYBODYBODY");
//		System.out.println(new String(reply.getBody()));
//		System.out.println("222 BODYBODYBODYBODYBODYBODYBODYBODYBODY");
		
		/*
		String text = "Custom RPC message";
		CustomMessage customMessage = new CustomMessage(88, text);
		Object response = rabbitTemplate.convertSendAndReceive(customMessage);
		
		System.out.println("################");
		System.out.println(response);
		System.out.println("################");
		
		// ===================
		
		AtomicInteger counter = new AtomicInteger();
		for (int i = 0; i < 10; i++) {

			int index = new Random().nextInt(2);
			String routing = routings[index];
			String message = "RabbitMQ Spring JSON Example Topic: " + routing;
			
			System.out.println("sending new custom [" + routing + "] message to topic...");
			
			CustomMessage customMessage = new CustomMessage(counter.incrementAndGet(),
					message);
			
			String exchange = "topic.exchange.name";
			rabbitTemplate.convertAndSend(exchange, routing, customMessage);
		}
*/
	}

//	@Override
//	public void onMessage(Message message) {
//		System.out.println("******************");
//		System.out.println(message);
//		System.out.println("******************");
//		
//	}


}
