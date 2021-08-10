package com.workshop.gke.order.service;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.client.RestTemplate;

import com.workshop.gke.order.service.api.OrdersApiDelegate;
import com.workshop.gke.order.service.model.Order;
import com.workshop.gke.order.service.repository.OrderRepository;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class OrderServiceApplication implements CommandLineRunner {

	@Autowired
	private OrderRepository orderRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceApplication.class);

	@Autowired
	private OrdersApiDelegate orders;

	@KafkaListener(topics = "order-requested", groupId = "order-requested-group", id = "3")
	public void listenGroupFoo3(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
			@Header(KafkaHeaders.OFFSET) int offsets) {

		LOGGER.info("====================================================================");
		LOGGER.info("Partition {}", partition);
		LOGGER.info("Offset {}", offsets);
		LOGGER.info("====================================================================");

		orders.create();
	}

	@KafkaListener(topics = "order-requested", groupId = "order-requested-group", id = "1")
	public void listenGroupFoo1(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
			@Header(KafkaHeaders.OFFSET) int offsets) {

		LOGGER.info("====================================================================");
		LOGGER.info("Partition {}", partition);
		LOGGER.info("Offset {}", offsets);
		LOGGER.info("====================================================================");

		orders.create();
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.workshop.gke.order.service.api")).build();
	}

	@Bean
	public RestTemplate pRestClient() {
		return new RestTemplate();
	}

	@Override
	public void run(String... args) throws Exception {

		Order order = Order.builder().createDate(new Date()).id(UUID.randomUUID()).status("open")
				.total(Double.valueOf(100.00)).build();

		orderRepository.save(order);

	}

}
