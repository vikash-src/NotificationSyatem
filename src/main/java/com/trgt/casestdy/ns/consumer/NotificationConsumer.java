package com.trgt.casestdy.ns.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.trgt.casestdy.ns.consumer.service.ConsumerService;

@Component
public class NotificationConsumer {
	
	@Autowired
	ConsumerService consumerService;
	
	@Autowired
	String[] topicNames;
	
	@KafkaListener(topics = "#{topicNames}")
	public void consume(String message) {
		consumerService.consumeMessage(message);
	}

}
