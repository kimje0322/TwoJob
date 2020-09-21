package com.blocker.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import com.blocker.dto.Property;
import com.blocker.dto.Userinfo;
import com.blocker.dto.Wallet;
import com.blocker.repository.UserInfoRepository;
import com.blocker.repository.WalletRepository;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

@Service
public class WalletServiceImpl implements WalletService{

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	Property property;
	
	@Override
	public String wallet_regist(String email, String address) {
		Optional<Userinfo> userinfo = userInfoRepository.findById(email);
		if(userinfo.isPresent()) {
			Optional<Wallet> wallets = walletRepository.findById(email);
			if(wallets.isPresent()) {
				return "isExist";
			}else {
				Wallet newWallet = new Wallet(email,address, 0);
				walletRepository.save(newWallet);
				return "success";
			}
		}else {
			return "novalid";
		}
	}
	@Override
	public String charge_ether(String email, Double ether) {
		Web3j web3j = Web3j.build(new HttpService());
		Optional<Wallet> wallet = walletRepository.findById(email);
		if(wallet.isPresent()) {
			String pk = property.getAdminPK();//"29c4cef2d28d4d5dbe56af250974a577d5a4e2e09eb88809bb51f7147cbc606c";
			Credentials credentials = Credentials.create(pk);
			RawTransactionManager rt = new RawTransactionManager(web3j, credentials);
			Transfer tf = new Transfer(web3j, rt);
			tf.sendFunds(wallet.get().getAddress(), BigDecimal.valueOf(ether), Convert.Unit.ETHER).sendAsync();
			return "success";
		}else {
			return "novalid";
		}
	}
	
	@Override
	public Object getBalance(String address) {
		Optional<Wallet> wallet = walletRepository.findByAddress(address);
		if(wallet.isPresent()) {
			return wallet.get().getBalance();
		}else {
			return "novalid"; 
		}
	}
	@Override
	public Object getWallet(String email) {
		Optional<Wallet> wallet = walletRepository.findById(email);
		if(wallet.isPresent()) {
			return wallet.get();
		}else {
			return "novalid"; 
		}
	}
}
