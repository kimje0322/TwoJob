package com.blocker.dto;

import java.util.Date;

import lombok.Data;

@Data
public class KakaoPayReadyDto {

	// response
	private String tid;
	private String next_redirect_pc_url;
	private Date created_at;

}