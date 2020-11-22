package com.trgt.casestdy.ns.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("com.trgt.casestdy")
@EnableAsync
public class NotificationSystem {
	
	public static void main(String[] args) {
		SpringApplication.run(NotificationSystem.class, args);
	}

}