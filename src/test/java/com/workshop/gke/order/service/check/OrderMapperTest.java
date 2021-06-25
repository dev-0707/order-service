package com.workshop.gke.order.service.check;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.workshop.gke.order.service.api.delegate.OrderMapper;
import com.workshop.gke.order.service.config.OrderServiceTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OrderServiceTestConfig.class)
public class OrderMapperTest {

	@Autowired
	private OrderMapper orderMapper;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_checkIban() {
		assertNotNull(orderMapper);
	}

}
