package xyz.cafeconleche.web.chica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import xyz.cafeconleche.web.chica.service.producer.ProducerPublishSubscribeService;

@Controller
public class MessengerPublishSubscribeController {

	
	@Autowired
	private ProducerPublishSubscribeService producerPublishSubscribeService;
	
	@RequestMapping(value="/msgs/producePublishSubscribe")
	public ModelAndView produce() {
		
		System.out.println("producing publish/subscribe...");
		producerPublishSubscribeService.produce();
		System.out.println("... produced publish/subscribe");
		
		ModelAndView modelAndView = new ModelAndView("messages.publish.subscribe");
		modelAndView.addObject("produced", true);
		return modelAndView;
	}
	
}
