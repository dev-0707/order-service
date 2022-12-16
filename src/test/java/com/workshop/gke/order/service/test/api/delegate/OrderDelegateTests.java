package com.workshop.gke.order.service.test.api.delegate;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.workshop.gke.order.service.api.OrdersApiDelegate;
import com.workshop.gke.order.service.dto.NewOrderDto;
import com.workshop.gke.order.service.dto.OrderDto;
import com.workshop.gke.order.service.exception.OrderNotFoundException;
import com.workshop.gke.order.service.model.Order;
import com.workshop.gke.order.service.repository.OrderRepository;
import com.workshop.gke.order.service.test.config.OrderServiceTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OrderServiceTestConfig.class)
public class OrderDelegateTests {

	@MockBean
	private OrderRepository orderRepository;

	@MockBean
	private RestTemplate paymentService;

	@Autowired
	private OrdersApiDelegate ordersApiDelegate;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCreateNewOrder() {
		Order order = generateOrder();
		Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);
		ResponseEntity<NewOrderDto> response = ordersApiDelegate.create();
		assertNotNull(response);
		assertEquals(CREATED, response.getStatusCode());
		assertEquals(order.getId(), response.getBody().getId());
	}

	@Test
	public void getAnExistingOrderShouldReturnOrder() {
		Order order = generateOrder();
		Mockito.when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
		ResponseEntity<OrderDto> response = ordersApiDelegate.getById(order.getId());
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(response.getBody().getId(), order.getId());
	}

	// @Test
	public void getNotExistingOrderShouldReturnOrderNotException() {
		Mockito.when(orderRepository.findById(UUID.randomUUID())).thenReturn(Optional.ofNullable(null));
		ResponseEntity<OrderDto> response = ordersApiDelegate.getById(UUID.randomUUID());
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void shouldReturnExistingOrderList() {
		List<Order> orders = new ArrayList<Order>();
		for (int i = 0; i < 10; i++) {
			orders.add(generateOrder());
		}
		Mockito.when(orderRepository.findAll()).thenReturn(orders);

		ResponseEntity<List<OrderDto>> response = ordersApiDelegate.getList();
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().size() > 0);
	}

	@Test
	public void deleteExistingOrderShouldBeDeleted() {
		Order order = generateOrder();
		Mockito.doNothing().when(orderRepository).deleteById(order.getId());
		ResponseEntity<Void> response = ordersApiDelegate.delete(order.getId());
		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
	}

	private Order generateOrder() {
		Order order = Order.builder().createDate(new Date()).status("open")
				.total(Double.valueOf(new Random().nextInt(100))).build();
		order.setId(UUID.randomUUID());
		return order;
	}

}
