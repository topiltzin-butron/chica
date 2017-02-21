package xyz.cafeconleche.web.chica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import xyz.cafeconleche.web.chica.service.producer.ProducerRoutingService;

@Controller
public class MessengerRoutingController {

	
	@Autowired
	private ProducerRoutingService producerRoutingService;
	
	@RequestMapping(value="/msgs/produceRouting")
	public ModelAndView produce() {
		
		System.out.println("producing routing...");
		producerRoutingService.produce();
		System.out.println("... produced routing");
		
		ModelAndView modelAndView = new ModelAndView("messages.routing");
		modelAndView.addObject("produced", true);
		return modelAndView;
	}
	
}
