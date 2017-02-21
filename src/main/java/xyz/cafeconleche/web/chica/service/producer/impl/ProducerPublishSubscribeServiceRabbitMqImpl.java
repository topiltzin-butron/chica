package xyz.cafeconleche.web.chica.service.producer.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.dto.CustomMessage;
import xyz.cafeconleche.web.chica.service.producer.ProducerPublishSubscribeService;

@Service("publishSubscribeService")
public class ProducerPublishSubscribeServiceRabbitMqImpl implements ProducerPublishSubscribeService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void produce() {

		System.out.println("produce work queue");

		AtomicInteger counter = new AtomicInteger();
		for (int i = 0; i < 10; i++) {
			System.out.println("sending new custom message to pub/sub ..");

			CustomMessage customMessage = new CustomMessage(counter.incrementAndGet(),
					"RabbitMQ Spring JSON Example PUB/SUB");
			
			String exchange = "publish.subscribe.exchange.name";
			rabbitTemplate.setExchange(exchange);
			rabbitTemplate.convertAndSend(customMessage);

		}

	}


}
