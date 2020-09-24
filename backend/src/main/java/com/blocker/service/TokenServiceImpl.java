package com.blocker.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.blocker.dto.InvestmentDto;
import com.blocker.dto.Member;
import com.blocker.dto.Property;
import com.blocker.dto.Wallet;
import com.blocker.repository.WalletRepository;
import com.blocker.wrapper.TJToken;

@Service
public class TokenServiceImpl implements TokenService{

	@Autowired
	Property property;
	@Autowired
	LoginService loginService;
	@Autowired
	WalletRepository walletRepository;
	@Override
	public void Deploy() {
		Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
		Credentials credentials = Credentials.create(property.getAdminPK());
		System.out.println("dd " + property.getAdminPK() + " " + credentials.getAddress());
		TJToken contract = null;
		try {
			contract = TJToken.deploy(web3j, credentials, new DefaultGasProvider()).send();
			System.out.println(contract.isValid());
			System.out.println(contract.getContractAddress());
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	@Override
	public String getTotal() {
		Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
		Credentials credentials = Credentials.create(property.getAdminPK());
		TJToken contract = TJToken.load(property.getTokenAddr(),web3j, credentials, new DefaultGasProvider());
		BigInteger total = new BigInteger("0"); 
		try {
			System.out.println(contract.isValid());
			total = contract.totalSupply().send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BigDecimal total2 = Convert.fromWei(total.toString(), Unit.ETHER);
		return total2.toString();
	}

	@Override
	public String getTokenNum(String accessToken) {
		Object result =  loginService.getUserInfo(accessToken);
		if(result.getClass() == Member.class) {
			Member m = (Member)result;
			Optional<Wallet> wallets = walletRepository.findById(m.getOauthId());
			if(wallets.isPresent()) {
				Wallet mywallet = wallets.get();
				Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
				Credentials credentials = Credentials.create(property.getAdminPK());
				TJToken contract = TJToken.load(property.getTokenAddr(),web3j, credentials, new DefaultGasProvider());
				BigInteger balance = new BigInteger("0"); 
				try {
					balance = contract.balanceOf(mywallet.getAddress()).send();
				} catch (Exception e) {
					e.printStackTrace();
				}
				BigDecimal balance2 = Convert.fromWei(balance.toString(), Unit.ETHER);
				return balance2.toString();
			}
			return "fail";
		}else if(result.getClass() == String.class){
			return (String)result;
		}else {
			return String.valueOf(result);
		}
	}
	@Override
	public String TransferTo(String accessToken, Integer amount) {
		Object result =  loginService.getUserInfo(accessToken);
		if(result.getClass() == Member.class) {
			Member m = (Member)result;
			Optional<Wallet> wallets = walletRepository.findById(m.getOauthId());
			if(wallets.isPresent()) {
				Wallet mywallet = wallets.get();
				Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
				Credentials credentials = Credentials.create(property.getAdminPK());
				TJToken contract = TJToken.load(property.getTokenAddr(),web3j, credentials, new DefaultGasProvider());
				BigInteger transvalue = Convert.toWei(String.valueOf(amount), Convert.Unit.ETHER).toBigInteger();
				try {
					TransactionReceipt tr = contract.transfer(mywallet.getAddress(), transvalue).send();
					System.out.println(tr.getBlockNumber());
					System.out.println(tr.getTransactionHash());
					return String.valueOf(Convert.fromWei(contract.balanceOf(mywallet.getAddress()).send().toString(), Unit.ETHER));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return "fail";
		}else if(result.getClass() == String.class){
			return (String)result;
		}else {
			return String.valueOf(result);
		}
	}
	@Override
	public void Transferfrm(String from, String to, Integer amount) {
		Web3j web3j = Web3j.build(new HttpService());
		Credentials sender = Credentials.create("5d338ca1076170e61e5630c9d55b3c1fd8e6d818be4d51e0007dede82ffa81af");
		TJToken contract = TJToken.load(property.getTokenAddr(),web3j, sender, new DefaultGasProvider());
		System.out.println("이거? " + contract.getContractAddress());
		try {
			System.out.println(contract.isValid() + " ?");
			contract.increaseAllowance("0x7823C51EcBCf5cAF58D5001A17cE30bc37C2Fc51", Convert.toWei(String.valueOf(amount), Convert.Unit.ETHER).toBigInteger()).send();
			System.out.println("???");
			System.out.println(contract.allowance("0x55766a23e337777Ba66129594600021C3A31E966", "0x7823C51EcBCf5cAF58D5001A17cE30bc37C2Fc51").send() + " ??D");
			TransactionReceipt tr = contract.transferFrom("0x55766a23e337777Ba66129594600021C3A31E966","0x7823C51EcBCf5cAF58D5001A17cE30bc37C2Fc51", Convert.toWei(String.valueOf(amount), Convert.Unit.ETHER).toBigInteger()).send();
			System.out.println(tr.getBlockNumber());
			System.out.println(tr.getTransactionHash());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

