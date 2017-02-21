package xyz.cafeconleche.web.chica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import xyz.cafeconleche.web.chica.service.producer.ProducerRpc2Service;
import xyz.cafeconleche.web.chica.service.producer.ProducerRpcService;

@Controller
public class MessengerRpcController {

	@Autowired
	private ProducerRpcService producerRpcService;
	
	@Autowired
	private ProducerRpc2Service producerRpc2Service;
	
	@RequestMapping(value="/msgs/produceRpc")
	public ModelAndView produce() {
		
		System.out.println("producing RPC...");
		producerRpcService.produce();
		System.out.println("... produced RPC");
		
		ModelAndView modelAndView = new ModelAndView("messages.rpc");
		modelAndView.addObject("produced", true);
		return modelAndView;
	}
	
	@RequestMapping(value="/msgs/produceRpc2")
	public ModelAndView produce2() {
		
		System.out.println("producing RPC 2...");
		producerRpc2Service.produce();
		System.out.println("... produced RPC 2");
		
		ModelAndView modelAndView = new ModelAndView("messages.rpc");
		modelAndView.addObject("produced", true);
		return modelAndView;
	}
	
}
