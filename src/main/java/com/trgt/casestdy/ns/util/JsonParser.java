package com.trgt.casestdy.ns.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonParser {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	public <T> T parseMessage(String message, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		T data = null;
		if(message!=null) {
			try {
				data = mapper.readValue(message,clazz);
			} catch (JsonProcessingException e1) {
				LOGGER.error("Error while parsing message for class: "+clazz.getName()+"\n"+message+"\n",e1);
			}
		}
		return data;
	}
}
