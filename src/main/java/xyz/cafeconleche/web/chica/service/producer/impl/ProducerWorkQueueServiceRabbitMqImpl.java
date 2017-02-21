package xyz.cafeconleche.web.chica.service.producer.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.dto.CustomMessage;
import xyz.cafeconleche.web.chica.service.producer.ProducerWorkQueueService;

@Service("producerWorkQueueService")
public class ProducerWorkQueueServiceRabbitMqImpl implements ProducerWorkQueueService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void produce() {

		System.out.println("produce work queue");

		AtomicInteger counter = new AtomicInteger();
		for (int i = 0; i < 10; i++) {
			System.out.println("sending new custom message to work queue..");

			CustomMessage customMessage = new CustomMessage(counter.incrementAndGet(),
					"RabbitMQ Spring JSON Example Work Queue");
			rabbitTemplate.convertAndSend("work.queue.name", customMessage);

		}

	}

}
