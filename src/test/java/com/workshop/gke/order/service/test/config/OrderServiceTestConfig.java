package com.workshop.gke.order.service.test.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.gke.order.service.api.OrdersApiDelegate;
import com.workshop.gke.order.service.api.delegate.OrderAPIDelegateImpl;
import com.workshop.gke.order.service.api.delegate.OrderMapper;
import com.workshop.gke.order.service.api.delegate.OrderMapperImpl;

@TestPropertySource(locations = "classpath:test.properties")
@TestConfiguration
public class OrderServiceTestConfig {

	@Bean
	public OrderMapper orderMapper() {
		return new OrderMapperImpl();
	}
	
	@Bean
	public OrdersApiDelegate orderDelegate() {
		return new OrderAPIDelegateImpl();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	
}
