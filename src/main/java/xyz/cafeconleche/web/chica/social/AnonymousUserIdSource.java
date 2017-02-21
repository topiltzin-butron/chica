package xyz.cafeconleche.web.chica.social;

import org.springframework.social.UserIdSource;

public class AnonymousUserIdSource implements UserIdSource{

	@Override
	public String getUserId() {
		System.out.println("--- AnonymousUserIdSource ---");
		return "anonymous";
	}

}
