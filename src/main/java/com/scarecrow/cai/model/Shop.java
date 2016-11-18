package com.scarecrow.cai.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Shop {

	private Long id;
	private String name;
	private City city;

	public Shop(Long id, String name, City city) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
	}

}
