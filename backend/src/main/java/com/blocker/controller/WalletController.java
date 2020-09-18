package com.blocker.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/wallet")
public class WalletController {
	
	
	@PostMapping("/makewallet")
	@ApiOperation(value = "[지갑 생성] 지갑을 생성합니다.")
	public void write(@RequestParam("password") String password) throws InterruptedException, ExecutionException, JsonProcessingException, CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
		
	}
	
	@PostMapping("/ether")
	@ApiOperation(value = "[지갑 생성] 지갑을 생성합니다.")
	public void dd() {

	}
}
