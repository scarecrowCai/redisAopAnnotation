package com.scarecrow.cai.service;

import com.scarecrow.cai.model.Order;

public interface OrderService {

	public Order getOrder(long id);

	public Order getOrder(long id, long customerId);

	public Order getOrder(Order order);

	public Order getOrder(Order order, long customerId);

}
