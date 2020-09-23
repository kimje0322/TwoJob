package com.blocker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@ApiOperation(value = "[펀딩 캠페인 오픈] 이더를 이용해 토큰을 구매합니다. param : [address, privatekey, ether], return : 충전후 잔액을 return 합니다")
	@GetMapping(value = "/transfer")
	public ResponseEntity<Void> transfer() throws Exception {
		fundingService.createCampaign();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@ApiOperation(value = "[토큰 사기] 이더를 이용해 토큰을 구매합니다. param : [address, privatekey, ether], return : 충전후 잔액을 return 합니다")
	@GetMapping(value = "/transfer2")
	public ResponseEntity<Void> transfer2() throws Exception {
		fundingService.fundingCampaign();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@ApiOperation(value = "[토큰 사기] 이더를 이용해 토큰을 구매합니다. param : [address, privatekey, ether], return : 충전후 잔액을 return 합니다")
	@GetMapping(value = "/transfer3")
	public ResponseEntity<Void> transfer3() throws Exception {
		fundingService.getCam();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@ApiOperation(value = "[토큰 사기] 이더를 이용해 토큰을 구매합니다. param : [address, privatekey, ether], return : 충전후 잔액을 return 합니다")
	@GetMapping(value = "/transfer4")
	public ResponseEntity<Void> transfer4() throws Exception {
		fundingService.receiveFund();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@ApiOperation(value = "[토큰 사기] 이더를 이용해 토큰을 구매합니다. param : [address, privatekey, ether], return : 충전후 잔액을 return 합니다")
	@GetMapping(value = "/transfer5")
	public ResponseEntity<Void> transfer5() throws Exception {
		fundingService.reFund();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
