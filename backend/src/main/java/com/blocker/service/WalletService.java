package com.blocker.service;

public interface WalletService {
	public String wallet_regist(String accessToken, String address, String privatekey);
	public String charge_ether(String accessToken, Double ether) throws Exception;
	public Object getBalance(String address);
	public Object getWallet(String id);
}
