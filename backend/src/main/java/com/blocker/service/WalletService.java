package com.blocker.service;

import java.io.IOException;

public interface WalletService {
	public String wallet_regist(String accessToken, String address, String privatekey);
	public String charge_ether(String accessToken, Double ether) throws Exception;
	public Object getBalance(String address) throws IOException;
	public Object getWallet(String id);
}
