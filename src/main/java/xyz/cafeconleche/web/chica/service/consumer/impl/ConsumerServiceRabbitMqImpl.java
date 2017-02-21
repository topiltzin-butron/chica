package xyz.cafeconleche.web.chica.service.consumer.impl;

import java.io.IOException;

import org.springframework.amqp.core.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.cafeconleche.web.chica.dto.CustomMessage;
import xyz.cafeconleche.web.chica.service.consumer.ConsumerService;

public class ConsumerServiceRabbitMqImpl implements ConsumerService {

	@Override
	public void onMessage(Message message) {
		System.out.println("--- CONSUMER ---");
		System.out.println(new String(message.getBody()));
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = null;
		try {
			actualObj = mapper.readTree(message.getBody());
			System.out.println("-------------------");
			System.out.println(actualObj);
			System.out.println("-------------------");
			
			System.out.println("++++++++++++++++");
			String body = new String(message.getBody());
			
//			CustomMessage customMessage = mapper.readValue(message.getBody(), CustomMessage.class);
			CustomMessage customMessage = mapper.readValue(body, CustomMessage.class);
			System.out.println(customMessage);
			System.out.println("++++++++++++++++");
			
		} catch (JsonProcessingException e) {
			System.err.println("JsonProcessingException");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException");
			e.printStackTrace();
		}
		
	}	
}
