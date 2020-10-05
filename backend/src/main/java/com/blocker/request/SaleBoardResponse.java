package com.blocker.request;

import lombok.Data;

@Data
public class SaleBoardResponse {
	String address;
	String investaddress;
	String userid;
	String url;
	String pjtname;
	String startdate;
	int saleprice;
	int totalpage;
	String picture;
	String editorhtml;
	String compname;
	String onelineintro;
	boolean isfinish;
}
