package com.blocker.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.blocker.service.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/wallet")
public class WalletController {
	
	@Autowired
	WalletService walletService;
	
	@ApiOperation(value = "[지갑 등록] 사용자 계정에 지갑을 등록합니다. param : [ email,  address ], result :  등록된 이메일이 아니면  novalid, 이미 존재하면 isExist, 등록에 성공하면 success를 return 합니다.")
	@PostMapping(value = "/regist")
	public ResponseEntity<String> register(@RequestParam("email") String email,@RequestParam("address") String address)  {
		return new ResponseEntity<String>(walletService.wallet_regist(email, address),HttpStatus.OK);
	}
	@PostMapping("/ether")
	@ApiOperation(value = "[이더 충전] 지갑에 ethere를 충전. param : [email, ether], result : 지갑이 존재하지 않으면 novalid, 충전에 성공하면 success를 return")
	public ResponseEntity<String> charge(@RequestParam("email") String email,@RequestParam("ether") Double ether) {
		return new ResponseEntity<String>(walletService.charge_ether(email, ether), HttpStatus.OK);
	}
	@ApiOperation(value = "[지갑 조회] address를 이용해 지갑의 값을 조회. param : [address], result : 지갑이 존재하지 않으면 novalid, 지갑이 존재하면 해당 지갑의 balance를 return")
	@GetMapping("/ToAdress")
	public ResponseEntity<?> get(@PathVariable String address) {
		Object result = walletService.getBalance(address);
		if(result.equals("novalid")) {
			return new ResponseEntity<String>((String) result, HttpStatus.OK);
		}else {
			return new ResponseEntity<Integer>((Integer) result, HttpStatus.OK);
		}
	}
	@ApiOperation(value = "[지갑 조회] 사용자 id를 이용해 지갑을 조회. param : [email}, result : 지갑이 존재하지 않으면 novalid, 지갑이 존재하면 해당 지갑을 return")
	@GetMapping("/ToEmail")
	public ResponseEntity<?> getByUser(@RequestParam("email") String email) {
		Object result = walletService.getWallet(email);
		if(result.equals("novalid")) {
			return new ResponseEntity<String>((String) result, HttpStatus.OK);
		}else {
			return new ResponseEntity<Integer>((Integer) result, HttpStatus.OK);
		}
	}
}
