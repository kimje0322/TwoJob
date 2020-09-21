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

import com.blocker.dto.Member;
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
	LoginService loginService;

	@Autowired
	Property property;

	@Override
	public String wallet_regist(String accessToken, String address) {
		Object result =  loginService.getUserInfo(accessToken);
		if(result.getClass() == Member.class) {
			Member m = (Member)result;
			Optional<Wallet> wallets = walletRepository.findById(m.getOauthId());
			if(wallets.isPresent()) {
				return "isExist";
			}else {
				Wallet newWallet = new Wallet(m.getOauthId(),address, 0);
				walletRepository.save(newWallet);
				return "success";
			}
		}else if(result.getClass() == String.class){
			return (String)result;
		}else {
			return String.valueOf(result);
		}
	}
	@Override
	public String charge_ether(String accessToken, Double ether) {

		Object result =  loginService.getUserInfo(accessToken);
		if(result.getClass() == Member.class) {
			Member m = (Member)result;
			Web3j web3j = Web3j.build(new HttpService());
			Optional<Wallet> wallet = walletRepository.findById(m.getOauthId());
			if(wallet.isPresent()) {
				String pk = property.getAdminPK();
				Credentials credentials = Credentials.create(pk);
				RawTransactionManager rt = new RawTransactionManager(web3j, credentials);
				Transfer tf = new Transfer(web3j, rt);
				tf.sendFunds(wallet.get().getAddress(), BigDecimal.valueOf(ether), Convert.Unit.ETHER).sendAsync();
				return "success";
			}else {
				return "novalid";
			}
		}else if(result.getClass() == String.class){
			return (String)result;
		}else {
			return String.valueOf(result);
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
	public Object getWallet(String id) {
		Optional<Wallet> wallet = walletRepository.findById(id);
		if(wallet.isPresent()) {
			return wallet.get();
		}else {
			return "novalid"; 
		}
	}
}
