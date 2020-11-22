package com.trgt.casestdy.ns.service;

import org.springframework.stereotype.Service;

import com.trgt.casestdy.ns.commons.model.Notification;

@Service
public interface NotificationService {
	
	void send(Notification notification);

}
