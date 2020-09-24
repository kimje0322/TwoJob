package com.blocker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.service.FundingService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/funding")
public class FundingController {
	@Autowired
	FundingService fundingService;
	@ApiOperation(value = "funding deploy하기, 건들지 마십숑")
	@PostMapping(value = "/deploy")
	public void Deploy() throws Exception {
		fundingService.Deploy();
	}
	@ApiOperation(value = "[BC][펀딩 캠페인 오픈] 펀딩받을 캠페인을 블록체인에 등록합니다. param : [accessToken, campaignId], return : 성공시 success, 지갑이 없을경우 noWallet, 캠페인이 없을 경우 noInvest")
	@GetMapping(value = "/createFunding")
	public ResponseEntity<String> createFunding(@RequestParam("accessToken") String accessToken, @RequestParam("canpaignId") String campaignId) throws Exception {
		return new ResponseEntity<String>(fundingService.createCampaign(accessToken,campaignId),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][펀딩 받기] 토큰을 이용해 원하는 캠페인에 펀딩합니다. param : [accessToken, campaignId, value(토큰값)], return : 성공시 success")
	@GetMapping(value = "/toFunding")
	public ResponseEntity<String> toFunding(@RequestParam("accessToken") String accessToken, @RequestParam("campaignId") String campaignId, @RequestParam("Value") String value) throws Exception {
		return new ResponseEntity<String>(fundingService.fundingCampaign(accessToken, campaignId, value),HttpStatus.OK);
	}
	
	@ApiOperation(value = "[BC][펀딩 금액 받기] 펀딩이 목표에 도달 했을 경우, 펀딩된 금액을 받습니다. param : [accessToken, campaignId], return : 성공시 success, 지갑이 없을경우 noWallet, 캠페인이 없을 경우 noInvest")
	@GetMapping(value = "/receiveFund")
	public ResponseEntity<String> receiveFund(@RequestParam("accessToken") String accessToken, @RequestParam("campaignId") String campaignId) throws Exception {
		return new ResponseEntity<String>(fundingService.receiveFund(accessToken,campaignId),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][펀딩 금액 환불] 펀딩이 목표에 도달 못 했을 경우, 투자자들에게 투자했던 금액을 돌려줍니다. param : [campaignId], return : ")
	@GetMapping(value = "/refund")
	public ResponseEntity<Void> refund(@RequestParam("campaignId") String campaignId) throws Exception {
		fundingService.reFund(campaignId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][펀딩 정보 검색 ] test용임 건들지 마세용 ")
	@GetMapping(value = "/test")
	public ResponseEntity<Void> transfer3(String campaignId) throws Exception {
		fundingService.getCam(campaignId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@ExceptionHandler(Exception.class)
	public void nullex(Exception e) {
		System.err.println("funding 부분에서 " + e.getClass());
	}
}
