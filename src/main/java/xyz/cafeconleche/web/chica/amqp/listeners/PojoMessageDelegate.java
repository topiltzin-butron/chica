package xyz.cafeconleche.web.chica.amqp.listeners;

public class PojoMessageDelegate {
	
	public String handleMezzage(Object source) {
		System.out.println("== PojoMessageDelegate.handleMessage ==");
		System.out.println("class: " + source.getClass());
		System.out.println("source: " + source);
//		return source.toUpperCase();
		return source.toString();
	}
	
	
}
