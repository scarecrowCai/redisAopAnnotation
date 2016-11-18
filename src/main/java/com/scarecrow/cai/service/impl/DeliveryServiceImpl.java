package com.scarecrow.cai.service.impl;

import org.springframework.stereotype.Service;

import com.scarecrow.cai.annotation.ExpireAnnotation;
import com.scarecrow.cai.annotation.ExpireAnnotation.ExpireType;
import com.scarecrow.cai.annotation.ReadAnnotation;
import com.scarecrow.cai.metadata.Constants;
import com.scarecrow.cai.model.City;
import com.scarecrow.cai.model.Shop;
import com.scarecrow.cai.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	private static final String DOMAIN = Constants.DOMAIN_DELIVERY;

	@Override
	@ReadAnnotation(domain = DOMAIN, clazz = City.class, params = "0")
	public City getCity(long cityId) {
		return new City(cityId, "上海");
	}

	@Override
	@ReadAnnotation(domain = DOMAIN, prifex = City.class, clazz = Shop.class, params = { "0" })
	public Shop getShop(long shopId) {
		return new Shop(shopId, "Nike", new City(1L, "上海"));
	}

	@Override
	@ExpireAnnotation(domain = DOMAIN, clazz = City.class, params = { "0.id" }, expireType = ExpireType.CLAZZ)
	public City updateCity(City city) {
		return city;
	}

	@Override
	@ExpireAnnotation(domain = DOMAIN, prifex = City.class, clazz = Shop.class, params = {
			"0.id" }, expireType = ExpireType.SELF)
	public Shop updateShop(Shop shop) {
		return shop;
	}

}
