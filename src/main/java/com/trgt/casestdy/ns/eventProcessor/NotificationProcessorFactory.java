package com.trgt.casestdy.ns.eventProcessor;

import org.springframework.stereotype.Component;

import com.trgt.casestdy.ns.commons.model.NotificationType;

@Component
public interface NotificationProcessorFactory {

	public NotificationProcessor getNotificationProcessor (NotificationType notificationType);
	
}
