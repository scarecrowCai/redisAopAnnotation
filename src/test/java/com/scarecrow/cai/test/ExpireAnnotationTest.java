package com.scarecrow.cai.test;

import javax.annotation.Resource;

import org.junit.Test;

import com.scarecrow.cai.model.City;
import com.scarecrow.cai.model.Shop;
import com.scarecrow.cai.service.DeliveryService;

public class ExpireAnnotationTest extends SpringTestCase {

	@Resource
	private DeliveryService deliveryService;

	@Test
	public void testExpireAnnotation() {
		deliveryService.getCity(1L);
		deliveryService.getShop(1L);
		City city = new City(1L, "北京");
		deliveryService.updateCity(city);
		deliveryService.getCity(1L);
		deliveryService.getShop(1L);
		Shop shop = new Shop(1L, "APPLE", new City(1L, "上海"));
		System.out.println(deliveryService.updateShop(shop));
		System.out.println(deliveryService.getCity(1L));
		System.out.println(deliveryService.getShop(1L));
	}

}
