package xyz.cafeconleche.web.chica.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.dao.GreetRepository;
import xyz.cafeconleche.web.chica.entity.Greeting;
import xyz.cafeconleche.web.chica.service.GreetingService;

@Service("greetingService")
public class GreetingServicePojoImpl implements GreetingService {

	@Autowired
	private GreetRepository greetRepository;

	@Override
	public List<Greeting> list() {
		List<Greeting> greetings = new ArrayList<>();
		greetRepository.findAll().forEach(g -> greetings.add(g));
		return greetings;
	}

	@Override
	public void add(Greeting greeting) {
		greetRepository.save(greeting);
	}

}
