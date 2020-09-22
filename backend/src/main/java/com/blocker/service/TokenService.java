package com.blocker.service;

import java.math.BigInteger;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface TokenService {
	public void Deploy();
	public String getTotal();
	public String getTokenNum(String address);
	public TransactionReceipt TransferTo(String to,String pk, BigInteger amount);
}
