package com.trgt.casestdy.ns.eventProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trgt.casestdy.ns.commons.model.NotificationType;

@Component
public class NotificationProcessorFactoryImpl implements NotificationProcessorFactory {

	@Autowired
	NotificationProcessor emailNotificationProcessor;
	
	@Override
	public NotificationProcessor getNotificationProcessor(NotificationType notificationType) {
		switch(notificationType) {
		case EMAIL : 
			return emailNotificationProcessor;
		default :
			return null;
		}
	}



}
