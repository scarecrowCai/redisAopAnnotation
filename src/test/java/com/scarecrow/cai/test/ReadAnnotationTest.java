package com.scarecrow.cai.test;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;

import com.scarecrow.cai.model.Order;
import com.scarecrow.cai.service.OrderService;

public class ReadAnnotationTest extends SpringTestCase {

	@Resource
	private OrderService orderService;

	@Ignore
	@Test
	public void testReadAnnotation() {
		orderService.getOrder(11);
		orderService.getOrder(11);
	}

	@Ignore
	@Test
	public void testReadAnnotation1() {
		Order mOrder = new Order(123L);
		orderService.getOrder(mOrder);
		orderService.getOrder(mOrder);
	}

	
	@Test
	public void testReadAnnotation2() {
		Long orderId = 111L;
		Long customerId = 111L;
		orderService.getOrder(orderId, customerId);
		orderService.getOrder(orderId, customerId);
	}

}
