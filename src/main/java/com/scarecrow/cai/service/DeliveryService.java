package com.scarecrow.cai.service;

import java.util.List;

import com.scarecrow.cai.model.City;
import com.scarecrow.cai.model.Shop;

public interface DeliveryService {

	public City getCity(long cityId);

	public List<City> getCities();

	public Shop getShop(long shopId);

	public City updateCity(City city);

	public Shop updateShop(Shop shop);

	public List<Shop> getCityShops(City city);

}
