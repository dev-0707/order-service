package com.workshop.gke.order.service.api.delegate;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.gke.order.service.api.OrdersApiDelegate;
import com.workshop.gke.order.service.dto.NewOrderDto;
import com.workshop.gke.order.service.dto.OrderDto;
import com.workshop.gke.order.service.model.Order;
import com.workshop.gke.order.service.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderDelegateImpl implements OrdersApiDelegate {

	@Value("${payment.service.api.url}")
	private String productServiceAPIUrl;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestTemplate paymentService;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	ObjectMapper objectMapper;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDelegateImpl.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public ResponseEntity<NewOrderDto> create() {
		Order order = Order.builder().createDate(new Date()).status("open")
				.total(Double.valueOf(new Random().nextInt(100))).build();
		String checkUri = productServiceAPIUrl + "/check";
		LOGGER.info("Invoking Payment Service at: {}", checkUri);
		paymentService.postForEntity(checkUri, Void.class, Void.class);
		orderRepository.save(order);
		NewOrderDto newOrder = new NewOrderDto();
		newOrder.setId(order.getId());
		try {
			kafkaTemplate.send("order-created", objectMapper.writeValueAsString(newOrder));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Void> delete(UUID id) {
		orderRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<OrderDto> getById(UUID id) {
		log.info("Getting order {}", id);

		if (orderRepository.findById(id).isPresent()) {
			return new ResponseEntity<>(orderMapper.toOrderDto(orderRepository.findById(id).get()), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<List<OrderDto>> getList() {
		return new ResponseEntity<>(orderMapper.toOrdersDto(orderRepository.findAll()), HttpStatus.OK);
	}
}
