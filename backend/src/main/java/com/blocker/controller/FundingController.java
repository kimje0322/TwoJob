package com.blocker.controller;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;

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

import com.blocker.dto.Chatroom;
import com.blocker.service.FundingService;
import com.blocker.util.webhook;

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
	@PostMapping(value = "/createFunding")
	public ResponseEntity<String> createFunding(@RequestParam("accessToken") String accessToken, @RequestParam("canpaignId") String campaignId) throws Exception {
		return new ResponseEntity<String>(fundingService.createCampaign(accessToken,campaignId),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][펀딩 받기] 토큰을 이용해 원하는 캠페인에 펀딩합니다. param : [accessToken, campaignId, value(토큰값)], return : 성공시 success")
	@PostMapping(value = "/toFunding")
	public ResponseEntity<String> toFunding(@RequestParam("accessToken") String accessToken, @RequestParam("campaignId") String campaignId, @RequestParam("Value") String value) throws Exception {
		return new ResponseEntity<String>(fundingService.fundingCampaign(accessToken, campaignId, value),HttpStatus.OK);
	}
	
	@ApiOperation(value = "[BC][펀딩 금액 받기] 펀딩이 목표에 도달 했을 경우, 펀딩된 금액을 받습니다. param : [accessToken, campaignId], return : 성공시 success, 지갑이 없을경우 noWallet, 캠페인이 없을 경우 noInvest")
	@PostMapping(value = "/receiveFund")
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
	@ApiOperation(value = "[BC][해당 투자에 펀딩한 사람의 수] 투자 아이디를 주면, 현재 해당 투자에 펀딩한 사람의 수를 return해줍니다. param : [campaignId], return : 펀딩한 사람의 수 ")
	@GetMapping(value = "/FunderNum")
	public ResponseEntity<String> FunderNum(String campaignId) throws Exception {
		return new ResponseEntity<String>(fundingService.getPepleNum(campaignId),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][해당 투자에 펀딩한 사람의 수] 투자 아이디를 주면, 현재 해당 투자에 펀딩한 사람의 수를 return해줍니다. param : [campaignId], return : 펀딩한 사람의 수 ")
	@PostMapping(value = "/SellItem")
	public ResponseEntity<String> SellItems(@RequestParam("accessToken") String accessToken, @RequestParam("campaignId") String campaignId, @RequestParam("count") Integer cnt, @RequestParam("money") Integer money) throws Exception {
		return new ResponseEntity<String>(fundingService.sellItem(accessToken, campaignId, cnt, money),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][펀딩 달성률] 투자 아이디를 주면, 현재 해당 투자에 펀딩한 사람의 수를 return해줍니다. param : [campaignId], return : 펀딩한 사람의 수 ")
	@PostMapping(value = "/fundingrate")
	public ResponseEntity<BigDecimal> fundingrate( @RequestParam("campaignId") String campaignId) throws Exception {
		return new ResponseEntity<BigDecimal>(fundingService.getfundingrate(campaignId),HttpStatus.OK);
	}
	
	@ApiOperation(value = "testg ")
	@GetMapping(value = "/testd")
	public void test() {
//		Chatroom c = new Chatroom();
//		System.out.println(3/0);
		
		int test = Integer.parseInt("dddd");

	}
//	@ExceptionHandler(Exception.class)
//	public void nullex(Exception e) {
//		StringWriter errors = new StringWriter();
//        e.printStackTrace(new PrintWriter(errors));
//        String result = errors.toString();
//		System.err.println("Funding 부분에서 " + e.getClass());
//		System.out.println(result);
//		webhook w = new webhook();
//		w.send("Funding 부분에서 " + e.getClass());
//		//w.postHttpsRequest(result, e.getClass().toString());
//	}
}
