package com.scarecrow.cai.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.scarecrow.cai.annotation.ReadAnnotation;
import com.scarecrow.cai.model.Order;
import com.scarecrow.cai.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	@ReadAnnotation(clazz = Order.class, domain = "Order", params = "0")
	public Order getOrder(long id) {
		logger.info("get from db");
		return new Order(id);
	}

	@Override
	@ReadAnnotation(clazz = Order.class, domain = "Order", params = { "0", "1" })
	public Order getOrder(long id, long customerId) {
		logger.info("get from db");
		return new Order(id, customerId);
	}

	@Override
	@ReadAnnotation(clazz = Order.class, domain = "Order", params = "0.id")
	public Order getOrder(Order order) {
		logger.info("get from db");
		return new Order(order.getId());
	}

	@Override
	@ReadAnnotation(clazz = Order.class, domain = "Order", params = { "0.id", "1" })
	public Order getOrder(Order order, long customerId) {
		logger.info("get from db");
		return new Order(order.getId());
	}

}
