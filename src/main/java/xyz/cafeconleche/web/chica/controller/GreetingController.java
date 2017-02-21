package xyz.cafeconleche.web.chica.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.datastax.driver.core.utils.UUIDs;

import xyz.cafeconleche.web.chica.entity.Greeting;
import xyz.cafeconleche.web.chica.service.GreetingService;

@Controller
public class GreetingController {
	
	@Autowired
	private GreetingService greetingService;
	
	@RequestMapping(value="/greeting", method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView greeting() {
		
		System.out.println("--- greeting ---");
		List<Greeting> greetings = greetingService.list();
		System.out.println("greetings: " + greetings);
		
		ModelAndView modelAndView = new ModelAndView("greeting");
		modelAndView.addObject("greetings", greetings);
		return modelAndView;
	}
	
//	@RequestMapping(value = "/greeting/{user}/",method = RequestMethod.GET)
//    @ResponseBody
//    public List<Greeting> greetingUserLimit(@PathVariable String user, Integer limit) {
//        
//		List<Greeting> greetings = new ArrayList<>();
////        greetRepository.findByUser(user, limit).forEach(g -> greetings.add(g));
//        return greetings;
//    }
	
//	@RequestMapping(value = "/greeting",method = RequestMethod.POST)
//    @ResponseBody
//    public String saveGreeting(@RequestBody Greeting greeting) {
//        
//		greeting.setCreationDate(new Date());
////        greetRepository.save(greeting);
//        return "OK";
//    }
	
	@RequestMapping(value="/greeting/add", method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView add() {
		
		System.out.println("--- greeting.add ---");
		ModelAndView modelAndView = new ModelAndView("greeting.add");
		modelAndView.addObject("greet", new Greeting());
		return modelAndView;
	}
	
	@RequestMapping(path = "/greeting/create", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView create(@ModelAttribute Greeting greeting, BindingResult result, Model model, HttpServletRequest request) {
		System.out.println("--- create ---");
		System.out.println("greeting: " + greeting);
		System.out.println("result: " + result);
		System.out.println("model: " + model);
		System.out.println("req: " + request);
		
		UUID id = UUIDs.timeBased();
		Date now = new Date();
		greeting.setId(id);
		greeting.setCreationDate(now);
		
		System.out.println("inserting...");
//		cassandraTemplate.insert(greeting);
		greetingService.add(greeting);
		
		System.out.println("...inserted");
		String statement = "select * from greetings";
//		List<Greeting> greetings = cassandraTemplate.select(statement, Greeting.class);
		List<Greeting> greetings = greetingService.list();
		System.out.println("greetings: " + greetings);
		
		ModelAndView modelAndView = new ModelAndView("greeting");
		modelAndView.addObject("greetings", greetings);
		modelAndView.addObject("successMessage", "Greeting added successfully!");
		
		return modelAndView;
	}

}
