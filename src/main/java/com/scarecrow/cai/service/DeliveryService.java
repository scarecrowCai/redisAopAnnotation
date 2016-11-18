package com.scarecrow.cai.service;

import com.scarecrow.cai.model.City;
import com.scarecrow.cai.model.Shop;

public interface DeliveryService {

	public City getCity(long cityId);

	public Shop getShop(long shopId);

	public City updateCity(City city);

	public Shop updateShop(Shop shop);

}
