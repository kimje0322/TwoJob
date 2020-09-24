package com.blocker.controller;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.blocker.service.TokenService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/Token")
public class TokenController {

	@Autowired
	TokenService tokenService;
	
	@ApiOperation(value = "Token deploy하기, 건들지 마십숑")
	@PostMapping(value = "/deploy")
	public void Deploy() throws Exception {
		tokenService.Deploy();
	}
	@ApiOperation(value = "[토큰 전체 발행량] 토큰 전체 발행량을 가져옵니다.")
	@GetMapping(value = "/total")
	public ResponseEntity<String> Total() throws Exception {
		return new ResponseEntity<String>(tokenService.getTotal(), HttpStatus.OK);
	}
	@ApiOperation(value = "[사용자의 토큰량] 특정 사용자의 토큰량을 가져옵니다. param : [address], return : 해당 address의 token 양")
	@GetMapping(value = "/balance")
	public ResponseEntity<String> getBalance(@RequestParam("address") String address) throws Exception {
		return new ResponseEntity<String>(tokenService.getTokenNum(address), HttpStatus.OK);
	}
	@ApiOperation(value = "[토큰 사기] 토큰을 구매합니다. param : [accessToken, money], return : 충전후 잔액을 return 합니다")
	@GetMapping(value = "/buy")
	public ResponseEntity<String> getBalance(@RequestParam("accessToken") String accessToken,@RequestParam("money") Integer money) throws Exception {
		return new ResponseEntity<String>(tokenService.TransferTo(accessToken, money), HttpStatus.OK);
	}
	@ApiOperation(value = "[토큰 전송] A에서 B로 토큰을 전달합니다.(손보는거 필요) param : [address, privatekey, ether], return : 충전후 잔액을 return 합니다")
	@GetMapping(value = "/transfer")
	public ResponseEntity<Void> transfer() throws Exception {
		tokenService.Transferfrm("", "", 1);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
