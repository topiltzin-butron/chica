package xyz.cafeconleche.web.chica.service.producer.impl;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.dto.CustomMessage;
import xyz.cafeconleche.web.chica.service.producer.ProducerRoutingService;

@Service("routingService")
public class ProducerRoutingServiceRabbitMqImpl implements ProducerRoutingService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private static String[] levels = {"info", "warn", "error"};

	
//	private static final String ERROR_BINDING_NAME = "error.binding.name";
//	private static final String INFO_BINDING_NAME = "info.binding.name";
//	private static final String WARN_BINDING_NAME = "warn.binding.name";
	
	@Override
	public void produce() {

		System.out.println("produce routing");

		AtomicInteger counter = new AtomicInteger();
		for (int i = 0; i < 10; i++) {

//			int index = new Random().nextInt(3);
			int index = new Random().nextInt(2);
			String level = levels[index];
//			String level = levels[0];
			String message = "RabbitMQ Spring JSON Example Routing level: " + level;
			
			System.out.println("sending new custom [" + level + "] message to routing...");
			
			CustomMessage customMessage = new CustomMessage(counter.incrementAndGet(),
					message);
			
			String exchange = "routing.exchange.name";
			rabbitTemplate.convertAndSend(exchange, level + ".binding.name", customMessage);
		}

	}


}
