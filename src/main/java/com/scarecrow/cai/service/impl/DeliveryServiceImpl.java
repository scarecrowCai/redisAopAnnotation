package com.scarecrow.cai.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.scarecrow.cai.annotation.ExpireAnnotation;
import com.scarecrow.cai.annotation.ExpireAnnotation.ExpireType;
import com.scarecrow.cai.annotation.ReadAnnotation;
import com.scarecrow.cai.metadata.Constants;
import com.scarecrow.cai.metadata.DataType;
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
	@ReadAnnotation(domain = DOMAIN, clazz = City.class, dataType = DataType.LIST)
	public List<City> getCities() {
		List<City> cities = new ArrayList<>();
		cities.add(new City(1L, "上海"));
		cities.add(new City(2L, "北京"));
		return cities;
	}

	@Override
	@ReadAnnotation(domain = DOMAIN, prefix = City.class, clazz = Shop.class, params = { "0" })
	public Shop getShop(long shopId) {
		return new Shop(shopId, "Nike", new City(1L, "上海"));
	}

	@Override
	@ExpireAnnotation(domain = DOMAIN, clazz = City.class, params = { "0.id" }, expireType = ExpireType.CLAZZ)
	public City updateCity(City city) {
		return city;
	}

	@Override
	@ExpireAnnotation(domain = DOMAIN, prefix = City.class, clazz = Shop.class, params = {
			"0.id" }, expireType = ExpireType.PREFIX)
	public Shop updateShop(Shop shop) {
		return shop;
	}

	@Override
	@ReadAnnotation(domain = DOMAIN, dataType = DataType.LIST, prefix = City.class, clazz = Shop.class)
	public List<Shop> getCityShops(City city) {
		List<Shop> shops = new ArrayList<Shop>();
		shops.add(new Shop(1L, "NIKE", city));
		shops.add(new Shop(2L, "APPLE", city));
		return shops;
	}

}
