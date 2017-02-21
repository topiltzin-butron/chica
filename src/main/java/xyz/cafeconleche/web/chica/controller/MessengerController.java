package xyz.cafeconleche.web.chica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import xyz.cafeconleche.web.chica.service.producer.ProducerService;

@Controller
public class MessengerController {

	@Autowired
	private ProducerService producerService;
	
	@RequestMapping(value="/msgs/produce")
	public ModelAndView produce() {
		
		System.out.println("producing...");
		producerService.produce();
		System.out.println("... produced");
		
		ModelAndView modelAndView = new ModelAndView("messages");
		modelAndView.addObject("produced", true);
		return modelAndView;
	}
	
}
