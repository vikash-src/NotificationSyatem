package com.trgt.casestdy.ns.kafka.config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
public class TopicConfigurations {
	private List<TopicConfiguration> topics = new ArrayList<TopicConfiguration>();

	@Bean("topicNames")
	public String[] getTopicNames() {
		return topics.stream().map(t -> t.getName()).toArray(String[]::new);
	}
	
	@Bean("maxPartition")
	public int maxPartition() {
		return topics.stream().map(t -> t.getNumPartitions()).max(Comparator.comparing(Integer::valueOf)).get();
	}
	
	public Optional<List<TopicConfiguration>> getTopics() {
		return Optional.ofNullable(topics);
	}
	
	List<TopicConfiguration> getTopicConfigurations(){
		return topics;
	}
	
	public void setTopics(List<TopicConfiguration> topics) {
		this.topics = topics;
	}

	public static class TopicConfiguration {
		@NotNull(message = "Topic name is required.")
		private String name;
		private Integer numPartitions;
		private Short replicationFactor;
		private boolean autoCreate;
		
		NewTopic toNewTopic() {
			return new NewTopic(this.name, this.numPartitions, this.replicationFactor);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getNumPartitions() {
			return numPartitions;
		}

		public void setNumPartitions(Integer numPartitions) {
			this.numPartitions = numPartitions;
		}

		public Short getReplicationFactor() {
			return replicationFactor;
		}

		public void setReplicationFactor(Short replicationFactor) {
			this.replicationFactor = replicationFactor;
		}

		public boolean isAutoCreate() {
			return autoCreate;
		}

		public void setAutoCreate(boolean autoCreate) {
			this.autoCreate = autoCreate;
		}
	}
}
