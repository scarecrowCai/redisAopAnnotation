package com.scarecrow.cai.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class City {

	private Long id;
	private String name;

	public City(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
