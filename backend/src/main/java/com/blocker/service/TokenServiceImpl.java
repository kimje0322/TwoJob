package com.blocker.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.blocker.dto.Property;
import com.blocker.wrapper.TJToken;

@Service
public class TokenServiceImpl implements TokenService{
	
	@Autowired
	Property property;
	
	@Override
	public void Deploy() {
		Web3j web3j = Web3j.build(new HttpService());
		Credentials credentials = Credentials.create(property.getAdminPK());
		System.out.println("dd " + property.getAdminPK());
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
		Web3j web3j = Web3j.build(new HttpService());
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
	public String getTokenNum(String address) {
		Web3j web3j = Web3j.build(new HttpService());
		Credentials credentials = Credentials.create(property.getAdminPK());
		TJToken contract = TJToken.load(property.getTokenAddr(),web3j, credentials, new DefaultGasProvider());
		BigInteger balance = new BigInteger("0"); 
		try {
			balance = contract.balanceOf(address).send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BigDecimal balance2 = Convert.fromWei(balance.toString(), Unit.ETHER);
		return balance2.toString();
	}
	@Override
	public TransactionReceipt TransferTo(String to,String pk, BigInteger amount) {
		Web3j web3j = Web3j.build(new HttpService());
		Credentials credentials = Credentials.create(property.getAdminPK());
		TJToken contract = TJToken.load(property.getTokenAddr(),web3j, credentials, new DefaultGasProvider());
		Credentials buyer = Credentials.create(pk);
		String hash = "";
		TransactionReceipt tr = null;
		try {
			BigDecimal val = new BigDecimal(amount);
			TransactionReceipt receipt = Transfer.sendFunds(web3j, buyer, property.getAdminAddr(),val, Unit.ETHER).send();
			tr = contract.transfer(to, amount).send();
			System.out.println(tr.getBlockNumber());
			System.out.println(tr.getTransactionHash());
			System.out.println(Convert.fromWei(contract.balanceOf(to).send().toString(), Unit.ETHER));
			hash = tr.getTransactionHash();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tr;
	}
}
