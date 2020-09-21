package com.blocker.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import com.blocker.service.TokenService;
import com.blocker.wrapper.Greeter;
import com.blocker.wrapper.ToJobCoin;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/Token")
public class TokenController {

	@Autowired
	TokenService tokenService;
	
	@ApiOperation(value = "지갑을 등록함")
	@RequestMapping(value = "/Token", method = RequestMethod.POST)
	public void register() throws Exception  {
		//Create credentials from private key
		Web3j web3j = Web3j.build(new HttpService());
		//Credentials credentials = WalletUtils.loadCredentials("jso","C:\\Users\\multicampus\\git\\s03p22b102\\backend\\src\\main\\resources\\admin.wallet");

		Credentials credentials = Credentials.create("20a02976be073d4e05d7ac24264532e101b4196816ba0472b4c051ead80b480f");

		Greeter contract = Greeter.deploy(web3j, credentials, new DefaultGasProvider(), "test").send();
		System.out.println(contract.getContractAddress());
		System.out.println(contract.greet().send());
		TransactionReceipt transactionReceipt = contract.newGreeting("Well hello again").send();
		System.out.println(contract.greet().send());


		for (Greeter.ModifiedEventResponse event : contract.getModifiedEvents(transactionReceipt)) {
			System.out.println("Modify event fired, previous value: " + event.oldGreeting
					+ ", new value: " + event.newGreeting);
			System.out.println("Indexed event previous value: " + Numeric.toHexString(event.oldGreetingIdx)
			+ ", new value: " + Numeric.toHexString(event.newGreetingIdx));
		}
	}

	@ApiOperation(value = "지갑을 등록함")
	@RequestMapping(value = "/Token2", method = RequestMethod.POST)
	public void register2() throws Exception  {
		//Create credentials from private key
		Web3j web3j = Web3j.build(new HttpService());
		Admin web3j2 = Admin.build(new HttpService());
		//Credentials credentials = WalletUtils.loadCredentials("jso","C:\\Users\\multicampus\\git\\s03p22b102\\backend\\src\\main\\resources\\admin.wallet");

		Credentials credentials = Credentials.create("197c6d5657d43e9b243c4c07d38df47a8b2923aca0c596844f1fccb2357bd87c");

		ToJobCoin contract = ToJobCoin.deploy(web3j, credentials, new DefaultGasProvider()).send();
		System.out.println(contract.getContractAddress());
		System.out.println(contract.balanceOf("0xe399E9B8B0d73Ef89ff674a749737B2808D75028").send());
		contract.transfer("0x50390f810827eb1c33072E0f4E80FDEd7FCe5E3E", new BigInteger("10000000000000000000")).send();
		System.out.println(contract.balanceOf("0xe399E9B8B0d73Ef89ff674a749737B2808D75028").send());
		System.out.println(contract.balanceOf("0x50390f810827eb1c33072E0f4E80FDEd7FCe5E3E").send());
		//Credentials credentials2 = Credentials.create("4eeb87af14240224d2a76f214a8fdf80365885e8850af4d9000fa000f502b5dd");

		//System.out.println(contract.balanceOf("0xb5a2cE9E688c50973021F120104E1a6050cF2253").send());
	}
	@ApiOperation(value = "지갑을 등록함")
	@RequestMapping(value = "/Token3", method = RequestMethod.POST)
	public void register3() throws Exception  {
		//Create credentials from private key
		Web3j web3j = Web3j.build(new HttpService());
		//Credentials credentials = WalletUtils.loadCredentials("jso","C:\\Users\\multicampus\\git\\s03p22b102\\backend\\src\\main\\resources\\admin.wallet");
		Credentials credentials = Credentials.create("20a02976be073d4e05d7ac24264532e101b4196816ba0472b4c051ead80b480f");
		ToJobCoin contract = ToJobCoin.load("0xaeFe8A46BB69382F4d844567BB664B3978Cf6Ffd",web3j, credentials, new DefaultGasProvider());
		System.out.println(contract.isValid());
		System.out.println(contract.balanceOf("0xb5a2cE9E688c50973021F120104E1a6050cF2253").send());
	}
	@ApiOperation(value = "Token deploy하기, 건들지 마십숑")
	@PostMapping(value = "/Deploy")
	public void Deploy() throws Exception {
		tokenService.Deploy();
	}
	
	@ApiOperation(value = "[토큰 전체 발행량] 토큰 전체 발행량을 가져옵니다.")
	@GetMapping(value = "/Total")
	public ResponseEntity<String> Total() throws Exception {
		return new ResponseEntity<String>(tokenService.getTotal(), HttpStatus.OK);
	}
	@ApiOperation(value = "[사용자의 토큰량] 특정 사용자의 토큰량을 가져옵니다. param : [address], return : 해당 address의 token 양")
	@GetMapping(value = "/balance")
	public ResponseEntity<String> getBalance(@RequestParam("address") String address) throws Exception {
		return new ResponseEntity<String>(tokenService.getTokenNum(address), HttpStatus.OK);
	}
	@ApiOperation(value = "[토큰 사기] 이더를 이용해 토큰을 구매합니다. param : [address, privatekey, ether], return : 충전후 잔액을 return 합니다")
	@GetMapping(value = "/buy")
	public ResponseEntity<TransactionReceipt> getBalance(@RequestParam("address") String address, @RequestParam("private key") String privatekey,@RequestParam("ether") BigInteger ether ) throws Exception {
		return new ResponseEntity<TransactionReceipt>(tokenService.TransferTo(address,privatekey, ether), HttpStatus.OK);
	}
}
