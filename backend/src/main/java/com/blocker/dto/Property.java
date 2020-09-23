package com.blocker.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class Property {
	@Value("${eth.admin.address}")
    private String AdminAddr;
	
	@Value("${eth.admin.privatekey}")
    private String AdminPK;
	
	@Value("${eth.erc20.contract}")
	private String TokenAddr;
	
	@Value("${eth.funding.contract}")
	private String FundingAddr;
	
	@Value("${kakao.loginkey}")
	private String kakaologinkey;
	
	
	
}
