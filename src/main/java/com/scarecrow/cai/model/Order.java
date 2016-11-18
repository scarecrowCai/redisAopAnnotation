package com.scarecrow.cai.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {

	private Long id;
	private Long customerId;

	public Order(Long id) {
		super();
		this.id = id;
	}

	public Order(Long id, Long customerId) {
		super();
		this.id = id;
		this.customerId = customerId;
	}

}
