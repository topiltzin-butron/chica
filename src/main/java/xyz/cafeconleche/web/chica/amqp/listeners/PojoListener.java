package xyz.cafeconleche.web.chica.amqp.listeners;

public class PojoListener {
	
	public String handleMessage(String source) {
		System.out.println("== rpc handleMessage ==");
		System.out.println("source: " + source);
		return source.toUpperCase();
	}

}
