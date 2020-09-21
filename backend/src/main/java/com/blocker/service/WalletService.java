package com.blocker.service;

public interface WalletService {
	public String wallet_regist(String email, String address);
	public String charge_ether(String email, Double ether);
	public Object getBalance(String address);
	public Object getWallet(String email);
}
