package com.scarecrow.cai.service.impl;

import com.scarecrow.cai.annotation.ReadAnnotation;
import com.scarecrow.cai.model.City;
import com.scarecrow.cai.model.Shop;
import com.scarecrow.cai.service.DeliveryService;

public class DeliveryServiceImpl implements DeliveryService {

	@Override
	@ReadAnnotation(domain = "Delivery", clazz = City.class, params = "0")
	public City getCity(long cityId) {
		return new City(1L, "上海");
	}

	@Override
	@ReadAnnotation(domain = "Delivery", prifex = City.class, clazz = Shop.class, params = { "0", "1" })
	public Shop getCityShop(long cityId, long shopId) {
		return new Shop(1L, "Nike", new City(1L, "上海"));
	}

}
