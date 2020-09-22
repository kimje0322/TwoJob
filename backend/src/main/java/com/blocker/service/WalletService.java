package com.blocker.service;

public interface WalletService {
	public String wallet_regist(String accessToken, String address);
	public String charge_ether(String accessToken, Double ether);
	public Object getBalance(String address);
	public Object getWallet(String id);
}
