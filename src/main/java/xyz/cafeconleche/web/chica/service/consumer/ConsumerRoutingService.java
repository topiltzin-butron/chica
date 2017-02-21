package xyz.cafeconleche.web.chica.service.consumer;

import org.springframework.amqp.core.MessageListener;

public interface ConsumerRoutingService extends MessageListener {

	void setConsumerId(String id);
	
}
