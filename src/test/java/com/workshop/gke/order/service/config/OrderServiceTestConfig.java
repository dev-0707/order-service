package com.workshop.gke.order.service.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

import com.workshop.gke.order.service.api.delegate.OrderMapper;
import com.workshop.gke.order.service.api.delegate.OrderMapperImpl;

@TestPropertySource(locations = "classpath:test.properties")
@TestConfiguration
public class OrderServiceTestConfig {

	@Bean
	public OrderMapper orderMapper() {
		return new OrderMapperImpl();
	}

}
