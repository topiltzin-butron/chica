package xyz.cafeconleche.web.chica.service.producer.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.dto.CustomMessage;
import xyz.cafeconleche.web.chica.service.producer.ProducerService;

@Service("producerService")
public class ProducerServiceRabbitMqImpl implements ProducerService{
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public void produce() {
		
		AtomicInteger counter = new AtomicInteger();
        for (int i = 0; i < 5; i++){
            System.out.println("sending new custom message..");
            
            String routingKey = "simple.queue.name";
			rabbitTemplate.setRoutingKey(routingKey);
            rabbitTemplate.convertAndSend(new CustomMessage(counter.incrementAndGet(), "RabbitMQ Spring JSON Example"));
        }
        
	}

}
