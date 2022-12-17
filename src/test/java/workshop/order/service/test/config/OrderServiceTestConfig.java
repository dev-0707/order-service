package workshop.order.service.test.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;

import workshop.order.service.api.OrdersApiDelegate;
import workshop.order.service.api.delegate.OrderAPIDelegateImpl;
import workshop.order.service.api.delegate.OrderMapper;
import workshop.order.service.api.delegate.OrderMapperImpl;

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
