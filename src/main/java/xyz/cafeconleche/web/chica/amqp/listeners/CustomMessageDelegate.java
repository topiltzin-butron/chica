package xyz.cafeconleche.web.chica.amqp.listeners;

import xyz.cafeconleche.web.chica.dto.CustomMessage;

public class CustomMessageDelegate {
	
	public CustomMessage handleMezzage(CustomMessage customMessage) {
		System.out.println("== CustomMessageDelegate.handleMezzage ==");
		System.out.println("class: " + customMessage.getClass());
		System.out.println("customMessage: " + customMessage);
		
		customMessage.setName(customMessage.getName().toUpperCase());
		
		return customMessage;
	}
	
	public String handleMessage(CustomMessage customMessage) {
		System.out.println("== CustomMessageDelegate.handleMezzage ==");
		System.out.println("class: " + customMessage.getClass());
		System.out.println("customMessage: " + customMessage);
		
		customMessage.setName(customMessage.getName().toUpperCase());
		
		return customMessage.toString();
	}
	
	
}
