package com.blocker.request;

import javax.persistence.Column;

import lombok.Data;

@Data
public class SaleBoardResponse {
	@Column
	String investaddress;
	@Column
	String userid;
	@Column
	String url;
	@Column
	String pjtname;
	@Column
	String startdate;
	@Column
	int saleprice;
	@Column
	String picture;
	@Column
	String editorhtml;
	@Column
	boolean isfinish;
}
