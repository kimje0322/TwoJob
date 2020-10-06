package com.blocker.controller;

import java.io.IOException;

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

import com.blocker.dto.Wallet;
import com.blocker.service.WalletService;
import com.blocker.util.webhook;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/wallet")
public class WalletController {
	
	@Autowired
	WalletService walletService;
	
	@ApiOperation(value = "[지갑 등록] 사용자 계정에 지갑을 등록합니다. param : [ accessToken,  address ], result : 토큰 만료면 error code, 이미 존재하면 isExist, 등록에 성공하면 success를 return 합니다.")
	@PostMapping(value = "/regist")
	public ResponseEntity<String> register(@RequestParam("accessToken") String accessToken,@RequestParam("address") String address, @RequestParam("privatekey") String privatekey)  {
		return new ResponseEntity<String>(walletService.wallet_regist(accessToken, address, privatekey),HttpStatus.OK);
	}
	@PostMapping("/ether")
	@ApiOperation(value = "[이더 충전] 지갑에 ethere를 충전. param : [email, ether], result : 토큰 만료면 error code,, 지갑이 존재하지 않으면 novalid, 충전에 성공하면 success를 return")
	public ResponseEntity<String> charge(@RequestParam("accessToken") String accessToken,@RequestParam("ether") Double ether) throws Exception {
		return new ResponseEntity<String>(walletService.charge_ether(accessToken, ether), HttpStatus.OK);
	}
	@ApiOperation(value = "[지갑 조회] address를 이용해 지갑의 값을 조회. param : [address], result : 지갑이 존재하지 않으면 novalid, 지갑이 존재하면 해당 지갑의 balance를 return")
	@GetMapping("/toaddress")
	public ResponseEntity<?> get(@RequestParam("address") String address) throws IOException {
		Object result = walletService.getBalance(address);
		return new ResponseEntity<String>((String) result, HttpStatus.OK);
		
	}
	@ApiOperation(value = "[지갑 조회] 사용자 id를 이용해 지갑을 조회. param : [oauthid], result : 지갑이 존재하지 않으면 novalid, 지갑이 존재하면 해당 지갑을 return")
	@GetMapping("/toid")
	public ResponseEntity<?> getByUser(@RequestParam("oauthid") String id) {
		Object result = walletService.getWallet(id);
		if(result.equals("novalid")) {
			return new ResponseEntity<String>((String) result, HttpStatus.OK);
		}else {
			return new ResponseEntity<Wallet>((Wallet) result, HttpStatus.OK);
		}
	}
	@ExceptionHandler(Exception.class)
	public void nullex(Exception e) {
		System.err.println("wallet 부분에서 " + e.getClass());
		webhook w = new webhook();
		w.send("wallet 부분에서 " + e.getClass());
	}
}
