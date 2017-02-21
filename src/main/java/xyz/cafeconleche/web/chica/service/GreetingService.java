package xyz.cafeconleche.web.chica.service;

import java.util.List;

import xyz.cafeconleche.web.chica.entity.Greeting;

public interface GreetingService {

	List<Greeting> list();
	
	void add(Greeting greeting);
	
}
