package com.scarecrow.cai.test;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;

import com.scarecrow.cai.model.City;
import com.scarecrow.cai.model.Shop;
import com.scarecrow.cai.service.DeliveryService;

public class ExpireAnnotationTest extends SpringTestCase {

	@Resource
	private DeliveryService deliveryService;

	@Ignore
	@Test
	public void testExpireClazz() {
		deliveryService.getCity(1L);
		deliveryService.getCities();
		City city = new City(1L, "北京");
		deliveryService.updateCity(city);
	}

	@Test
	public void testExpirePrefix() {
		deliveryService.getCities();
		deliveryService.getCityShops(new City(1L, "上海"));
		deliveryService.getShop(1L);
		deliveryService.updateShop(new Shop(1L, "Adidas", new City(1L, "上海")));
	}

}
