package com.trgt.casestdy.ns.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	
	@Value("${spring.kafka.consumer.bootstrap-servers}")
	String bootstrapServers;
	
	@Value("${spring.kafka.consumer.group-id}")
	String groupId;
	
	@Value("${spring.kafka.consumer.auto-offset-reset}")
	String autoOffSet;
	
	@Value("${spring.kafka.consumer.key-deserializer}")
	String keyDeserializer;
	
	@Value("${spring.kafka.consumer.value-deserializer}")
	String valueDeserializer;

	@Value("${spring.kafka.consumer.enable.auto.commit}")
	String enableAutoCommit;
	
	@Autowired
	@Qualifier("maxPartition")
	int maxPollRecords;
	
	@Value("${spring.kafka.consumer.allow.auto.create.topics}")
	boolean autoCreateTopics;
	
	@Value("${spring.kafka.consumer.enable.kafka}")
	boolean enableKafka;
	
	@Bean
	Map<String, Object> consumerConfigs(){
		Map<String, Object> props = new HashMap<>(); 
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffSet);
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
		props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, autoCreateTopics);
		return props;
	}
	
	@Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(maxPollRecords);
        factory.setAutoStartup(enableKafka);
        return factory;
    }
	

}
