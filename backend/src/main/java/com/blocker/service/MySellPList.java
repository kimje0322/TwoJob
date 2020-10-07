package com.blocker.service;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MySellPList {
	private String picture;
	private String pjtName;
	private String oneLineIntro;
	private String price;
	private Date time;
	public MySellPList(String picture, String pjtName, String oneLineIntro, String price,Date time) {
		super();
		this.picture = picture;
		this.pjtName = pjtName;
		this.oneLineIntro = oneLineIntro;
		this.price = price;
		this.time = time;
	}
}
