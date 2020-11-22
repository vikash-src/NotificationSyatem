package com.trgt.casestdy.ns.kafka.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.GenericWebApplicationContext;

import com.trgt.casestdy.ns.kafka.config.TopicConfigurations.TopicConfiguration;

@Configuration
public class TopicAdmin {
	private static final Logger log = LoggerFactory.getLogger(TopicAdmin.class);
	
	private TopicConfigurations configurations;
	private GenericWebApplicationContext context;
	public TopicAdmin(
			TopicConfigurations configurations,
			GenericWebApplicationContext genericContext) {
		this.configurations = configurations;
		this.context = genericContext;
	}

	@PostConstruct
	public void createTopics() {
		configurations
				.getTopics()
				.ifPresent(this::initializeBeans);
	}

	private void initializeBeans(List<TopicConfiguration> topics) {
		topics.forEach(t -> {
			log.info(
					"topic={},numPartitions={},replicationFactor={}",
					t.getName(),
					t.getNumPartitions(),
					t.getReplicationFactor()
			);
			if(t.isAutoCreate())
				context.registerBean(t.getName(), NewTopic.class, t::toNewTopic);
		});
	}
}
