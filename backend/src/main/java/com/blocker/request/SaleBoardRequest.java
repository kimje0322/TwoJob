package com.blocker.request;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;

@Data
public class SaleBoardRequest {

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
}
