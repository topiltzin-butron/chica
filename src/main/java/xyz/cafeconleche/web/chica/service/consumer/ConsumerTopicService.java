package xyz.cafeconleche.web.chica.service.consumer;

import org.springframework.amqp.core.MessageListener;

public interface ConsumerTopicService extends MessageListener {

	void setConsumerId(String string);

}
