package com.blocker.controller;

import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;

import org.apache.http.auth.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.abi.Utils;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/wallet")
public class WalletController {
	
	
	@PostMapping("/makewallet")
	@ApiOperation(value = "[지갑 생성] 지갑을 생성합니다.")
	public void write(@RequestParam("password") String password) throws InterruptedException, ExecutionException, JsonProcessingException, CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
		WalletFile walletFile;
		ECKeyPair ecKeyPair = Keys.createEcKeyPair();
		walletFile = Wallet.createStandard(password, ecKeyPair);
		System.out.println("address " + walletFile.getAddress());
		ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		String jsonStr = objectMapper.writeValueAsString(walletFile);
		System.out.println("keystore json file " + jsonStr);
	}
	
	@PostMapping("/ether")
	@ApiOperation(value = "[지갑 생성] 지갑을 생성합니다.")
	public void dd() {
		Web3j web3j = Web3j.build(new HttpService());
		String privateKey = "{your private key}";
//		TransactionReceipt transferReceipt = Transfer.sendFunds(
//           web3j, credentials,
//           "0x19e03255f667bdfd50a32722df860b1eeaf4d635",  // you can put any address here
//           BigDecimal.ONE, Convert.Unit.WEI)  // 1 wei = 10^-18 Ether
//           .send();
	}
}
/*String privateKey = "{your private key}";
Credentials cs = Credentials.create(privateKey);

String privateKey = cs.getEcKeyPair().getPrivateKey().toString(16);
String publicKey = cs.getEcKeyPair().getPublicKey().toString(16);
String addr = cs.getAddress();

System.out.println("Private key: " + privateKey);
System.out.println("Public key: " + publicKey);
System.out.println("Address: " + addr);
*
*
*/