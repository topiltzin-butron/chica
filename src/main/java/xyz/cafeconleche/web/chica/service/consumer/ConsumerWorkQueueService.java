package xyz.cafeconleche.web.chica.service.consumer;

import org.springframework.amqp.core.MessageListener;

public interface ConsumerWorkQueueService extends MessageListener {

	void setConsumerId(String id);
	
}
