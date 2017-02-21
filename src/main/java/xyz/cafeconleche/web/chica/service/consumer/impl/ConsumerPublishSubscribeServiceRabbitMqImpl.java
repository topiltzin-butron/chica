package xyz.cafeconleche.web.chica.service.consumer.impl;

import java.io.IOException;
import java.util.Random;

import org.springframework.amqp.core.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.cafeconleche.web.chica.dto.CustomMessage;
import xyz.cafeconleche.web.chica.service.consumer.ConsumerPublishSubscribeService;

public class ConsumerPublishSubscribeServiceRabbitMqImpl implements ConsumerPublishSubscribeService {

	private String id;

	@Override
	public void setConsumerId(String id) {
		this.id = id;
	}
	
	@Override
	public void onMessage(Message message) {

		System.out.println("-- onMessage -- consumer: " + id);
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			String body = new String(message.getBody());
			CustomMessage customMessage = mapper.readValue(body, CustomMessage.class);
			System.out.println(id + " " + customMessage);
		} catch (JsonProcessingException e) {
			System.err.println(id + " " + "JsonProcessingException");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(id + " " + "IOException");
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(100 * (new Random().nextInt(20)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
		
}
