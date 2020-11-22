package com.trgt.casestdy.ns.eventProcessor;

import org.springframework.stereotype.Service;

import com.trgt.casestdy.ns.commons.model.Notification;

@Service
public interface NotificationProcessor {

	void process(Notification notification);

}
