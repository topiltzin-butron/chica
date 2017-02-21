package xyz.cafeconleche.web.chica.service.producer.impl;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.dto.CustomMessage;
import xyz.cafeconleche.web.chica.service.producer.ProducerRpcService;

@Service("rpcService")
public class ProducerRpcServiceRabbitMqImpl implements ProducerRpcService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private DirectExchange ex;
	@Autowired
	private DirectExchange replyExchange;
	
	@Override
	public void produce() {

		String routing = "routing.test.name";
		System.out.println("produce rpc");
		System.out.println("reply address: " + replyExchange.getName() + "/" + routing);
		

		rabbitTemplate.setExchange(ex.getName());
		rabbitTemplate.setRoutingKey("test");
		rabbitTemplate.setReplyAddress(replyExchange.getName() + "/" + routing);
		
//		rabbitTemplate.setReplyAddress(replyExchange.getName() + "/" + replyQueue.getName());
		
		Message message = MessageBuilder.withBody("foo".getBytes())
				.setContentType("text/plain")
				.build();
		
		Message reply = rabbitTemplate.sendAndReceive(message);
		
		System.out.println("REPLYPLEYREPLYREPLYREPLYREPLYREPLY");
		System.out.println(reply);
		System.out.println("REPLYPLEYREPLYREPLYREPLYREPLYREPLY");
		
		System.out.println("BODYBODYBODYBODYBODYBODYBODYBODYBODY");
		System.out.println(new String(reply.getBody()));
		System.out.println("BODYBODYBODYBODYBODYBODYBODYBODYBODY");
		
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
