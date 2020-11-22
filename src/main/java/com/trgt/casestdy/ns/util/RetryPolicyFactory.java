package com.trgt.casestdy.ns.util;

import java.time.Duration;

import com.trgt.casestdy.ns.commons.model.NotificationType;
import com.trgt.casestdy.ns.exception.NotSentException;

import net.jodah.failsafe.RetryPolicy;

public class RetryPolicyFactory {

	private RetryPolicyFactory() {}

	private static class SingletonHelper {

		private static final RetryPolicy<Object> DEFAULT_INSTANCE = new RetryPolicy<>()
				.handle(NotSentException.class)
				.withDelay(Duration.ofSeconds(10))
				.withMaxRetries(3)
				.abortOn(Exception.class);	
	}

	public static RetryPolicy getRetryPolicy(NotificationType notificationType) {
		switch(notificationType) {
		case EMAIL : 
		default : return SingletonHelper.DEFAULT_INSTANCE;
		}
	}

}
