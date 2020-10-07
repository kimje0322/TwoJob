package com.blocker.dto;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class receipt {
	String img;
	int price;
	public receipt() {}
	public receipt(String img, int price) {
		super();
		this.img = img;
		this.price = price;
	}
	
}
