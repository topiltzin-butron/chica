package xyz.cafeconleche.web.chica.service.producer.impl;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.dto.CustomMessage;
import xyz.cafeconleche.web.chica.service.producer.ProducerTopicService;

@Service("topicService")
public class ProducerTopicServiceRabbitMqImpl implements ProducerTopicService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private static String[] routings = {"quick.orange.rabbit", "lazy.black.cat"};
	
	@Override
	public void produce() {

		System.out.println("produce routing");

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

	}


}
