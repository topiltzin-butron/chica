package xyz.cafeconleche.web.chica.service.consumer;

import org.springframework.amqp.core.MessageListener;

public interface ConsumerPublishSubscribeService extends MessageListener {

	void setConsumerId(String id);

}
