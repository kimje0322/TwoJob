package com.blocker.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface TokenService {
	public void Deploy();
	public String getTotal();
	public String getTokenNum(String address);
	public String TransferTo(String to,Integer amount);
	public void Transferfrm(String from, String to, Integer amount);
	public String getAdminVal();
	public String IncreaseToken(Integer amount);
}
