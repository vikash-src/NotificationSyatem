package com.trgt.casestdy.ns.consumer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trgt.casestdy.ns.commons.model.Notification;
import com.trgt.casestdy.ns.consumer.service.ConsumerService;
import com.trgt.casestdy.ns.eventProcessor.NotificationProcessor;
import com.trgt.casestdy.ns.eventProcessor.NotificationProcessorFactory;
import com.trgt.casestdy.ns.util.JsonParser;

@Service
public class ConsumerServiceImpl implements ConsumerService {

	@Autowired
	NotificationProcessorFactory notificationProcessorFactory;
	
	@Autowired
	JsonParser parser;
	
	@Override
	public void consumeMessage(String message) {
		Notification notification = parser.parseMessage(message, Notification.class);
		NotificationProcessor processor = notificationProcessorFactory.getNotificationProcessor(notification.getNotificationType());
		processor.process(notification);
	}

}
