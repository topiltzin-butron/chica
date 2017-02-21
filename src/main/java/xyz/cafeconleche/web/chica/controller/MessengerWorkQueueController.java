package xyz.cafeconleche.web.chica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import xyz.cafeconleche.web.chica.service.producer.ProducerWorkQueueService;

@Controller
public class MessengerWorkQueueController {

	@Autowired
	private ProducerWorkQueueService producerWorkQueueService;
	
	@RequestMapping(value="/msgs/produceWorkQueue")
	public ModelAndView produce() {
		
		System.out.println("producing work queue...");
		producerWorkQueueService.produce();
		System.out.println("... produced work queue");
		
		ModelAndView modelAndView = new ModelAndView("messages.workqueue");
		modelAndView.addObject("produced", true);
		return modelAndView;
	}
	
}
