package com.scarecrow.cai.service;

import com.scarecrow.cai.model.City;
import com.scarecrow.cai.model.Shop;

public interface DeliveryService {

	public City getCity(long cityId);

	public Shop getCityShop(long cityId, long shopId);

}
