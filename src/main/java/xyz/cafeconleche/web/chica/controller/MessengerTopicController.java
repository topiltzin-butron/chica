package xyz.cafeconleche.web.chica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import xyz.cafeconleche.web.chica.service.producer.ProducerTopicService;

@Controller
public class MessengerTopicController {

	
	@Autowired
	private ProducerTopicService producerTopicService;
	
	@RequestMapping(value="/msgs/produceTopic")
	public ModelAndView produce() {
		
		System.out.println("producing routing...");
		producerTopicService.produce();
		System.out.println("... produced routing");
		
		ModelAndView modelAndView = new ModelAndView("messages.topic");
		modelAndView.addObject("produced", true);
		return modelAndView;
	}
	
}
