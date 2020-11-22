package com.trgt.casestdy.ns.eventProcessor.impl;

import org.springframework.stereotype.Component;

import com.trgt.casestdy.ns.commons.model.Notification;
import com.trgt.casestdy.ns.eventProcessor.NotificationProcessor;

@Component
public abstract class AbstractNotificationProcessor implements NotificationProcessor{

	@Override
	public void process(Notification notification) {
		if(isEnabled() && isValid(notification)) {
			processNotification(notification);
		}
	}
	
	protected abstract void processNotification(Notification notification);

	protected abstract boolean isValid(Notification notification);

	protected abstract boolean isEnabled();

}
