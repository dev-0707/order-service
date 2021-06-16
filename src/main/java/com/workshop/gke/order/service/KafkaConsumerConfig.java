package com.workshop.gke.order.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import io.opentracing.Tracer;
import io.opentracing.contrib.kafka.spring.TracingConsumerFactory;
import io.opentracing.contrib.kafka.spring.TracingKafkaAspect;

@Configuration
public class KafkaConsumerConfig {

	@Value(value = "${kafka.bootstrapAddress:localhost:9092}")
	private String bootstrapAddress;

	@Autowired
	private Tracer tracer;

	@Bean
	public ConsumerFactory<Integer, String> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return new TracingConsumerFactory<>(new DefaultKafkaConsumerFactory<>(props), tracer);

	}

	@Bean
	public TracingKafkaAspect tracingKafkaAspect() {
		return new TracingKafkaAspect(tracer);
	}

}
