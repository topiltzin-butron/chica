package xyz.cafeconleche.web.chica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.cafeconleche.web.chica.entity.Greeting;
import xyz.cafeconleche.web.chica.entity.GreetingDetails;
import xyz.cafeconleche.web.chica.service.GreetingService;

@RestController
//@RequestMapping("/rest/greetings")
@RequestMapping(path="/rest/greetings", produces = MediaType.APPLICATION_JSON_VALUE )
public class GreetingRESTController {

	@Autowired
	private GreetingService greetingService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Greeting> list() {
		System.out.println("--- rest list ---");
		return greetingService.list();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{user}")
	public @ResponseBody Greeting get(@PathVariable String user) throws JsonProcessingException {

		Greeting greeting = null;

		List<Greeting> greetings = greetingService.list();
		if (greetings != null && !greetings.isEmpty()) {
			for (Greeting g : greetings) {
				if (user.equals(g.getUser())) {
					greeting = g;
					System.out.println("Found: " + user);
					break;
				}
			}
		}

		System.out.println("Returning: " + greeting);
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("can serialize? " + mapper.canSerialize(Greeting.class));
		String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(greeting);
		System.out.println("--- jsonInString ---");
		System.out.println(jsonInString);

		
		System.out.println("can? " + mapper.canSerialize(Greeting.class));
		
		return greeting;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/details/{user}")
	public @ResponseBody GreetingDetails getDetails(@PathVariable String user) throws JsonProcessingException {

		GreetingDetails greetingDetails = new GreetingDetails();
		

		List<Greeting> greetings = greetingService.list();
		if (greetings != null && !greetings.isEmpty()) {
			for (Greeting g : greetings) {
				if (user.equals(g.getUser())) {
					greetingDetails.setGreeting(g);
					System.out.println("Found: " + user);
					break;
				}
			}
		}
		
		greetingDetails.setGreetings(greetings);
		greetingDetails.setActive(true);
		
		return greetingDetails;
	}

}
