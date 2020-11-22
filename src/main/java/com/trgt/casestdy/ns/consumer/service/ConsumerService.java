package com.trgt.casestdy.ns.consumer.service;

import org.springframework.stereotype.Service;

@Service
public interface ConsumerService {
	void consumeMessage(String message);
}
