package xyz.cafeconleche.web.chica.entity;

import java.io.Serializable;
import java.util.List;

public class GreetingDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Greeting greeting;
	private List<Greeting> greetings;
	private boolean active;

	public Greeting getGreeting() {
		return greeting;
	}

	public void setGreeting(Greeting greeting) {
		this.greeting = greeting;
	}

	public List<Greeting> getGreetings() {
		return greetings;
	}

	public void setGreetings(List<Greeting> greetings) {
		this.greetings = greetings;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
