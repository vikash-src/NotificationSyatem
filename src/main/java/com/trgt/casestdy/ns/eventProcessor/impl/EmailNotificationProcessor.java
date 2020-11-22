package com.trgt.casestdy.ns.eventProcessor.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.trgt.casestdy.ns.commons.model.Notification;
import com.trgt.casestdy.ns.commons.model.NotificationType;
import com.trgt.casestdy.ns.service.NotificationService;

@Service
public class EmailNotificationProcessor extends AbstractNotificationProcessor{

	@Value("${email.processor.enable}")
	private boolean enableEmailProcessor;
	
	@Autowired
	NotificationService emailNotificationService;
	
	@Override
	protected void processNotification(Notification notification) {
		//add auditing 
		emailNotificationService.send(notification);
	}

	@Override
	protected boolean isValid(Notification notification) {
		if(null != notification.getNotificationType() && NotificationType.EMAIL == notification.getNotificationType())
			return true;
		return false;
	}

	@Override
	protected boolean isEnabled() {
		return enableEmailProcessor;
	}



}
