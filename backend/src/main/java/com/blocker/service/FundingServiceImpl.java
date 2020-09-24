package com.blocker.service;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import com.blocker.dto.InvestmentDto;
import com.blocker.dto.Member;
import com.blocker.dto.Property;
import com.blocker.dto.Wallet;
import com.blocker.repository.InvestmentRepository;
import com.blocker.repository.WalletRepository;
import com.blocker.wrapper.CrowdFunding;
import com.blocker.wrapper.TJToken;

@Service
public class FundingServiceImpl implements FundingService{

	@Autowired
	Property property;
	@Autowired
	LoginService loginService;
	@Autowired
	WalletRepository walletRepository;
	@Autowired
	InvestmentRepository investmentRepository;
	@Override
	public void Deploy() {
		Web3j web3j = Web3j.build(new HttpService());
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = null;
		try {
			contract = CrowdFunding.deploy(web3j, credentials, new DefaultGasProvider()).send();
			System.out.println(contract.isValid());
			System.out.println(contract.getContractAddress());
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	@Override
	public String createCampaign(String accessToken, String id) throws Exception {
		Object result =  loginService.getUserInfo(accessToken);
		if(result.getClass() == Member.class) {
			Member m = (Member)result;
			Optional<Wallet> wallets = walletRepository.findById(m.getOauthId());
			Optional<InvestmentDto> Invest = investmentRepository.findById(id);
			if(wallets.isPresent()) {
				if(Invest.isPresent()) {
					Wallet mywallet = wallets.get();
					InvestmentDto myInvest = Invest.get();
					Web3j web3j = Web3j.build(new HttpService());
					Credentials credentials = Credentials.create(mywallet.getPrivatekey());
					CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
					contract.createCampaign(String.valueOf(myInvest.getNum()), Convert.toWei(String.valueOf(myInvest.getGoalprice()), Convert.Unit.ETHER).toBigInteger(), new BigInteger("123123123123")).send();
					System.out.println("캠페인 생성 완료!");
					return "success";
				}else {
					return "noInvest";
				}
			}else {
				return "noWallet";
			}
		}else if(result.getClass() == String.class){
			return (String)result;
		}else {
			return String.valueOf(result);
		}
	}
	@Override
	public String fundingCampaign(String accessToken, String campaignid, String value) throws Exception {
		Object result =  loginService.getUserInfo(accessToken);
		if(result.getClass() == Member.class) {
			Member m = (Member)result;
			Optional<Wallet> wallets = walletRepository.findById(m.getOauthId());
			Optional<InvestmentDto> Invest = investmentRepository.findById(campaignid);
			if(wallets.isPresent()) {
				if(Invest.isPresent()) {
					Wallet mywallet = wallets.get();
					InvestmentDto myInvest = Invest.get();
					Web3j web3j = Web3j.build(new HttpService());
					Credentials credentials = Credentials.create(mywallet.getPrivatekey());
					CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());

					System.out.println("후 = " +contract.isValid());
					contract.FundingCampign(property.getTokenAddr(),credentials.getAddress(), String.valueOf(myInvest.getNum()), Convert.toWei(value, Convert.Unit.ETHER).toBigInteger()).send();
					System.out.println("fundingCampaign succss");
					return "success";
				}else {
					return "noInvest";
				}
			}else {
				return "noWallet";
			}
		}else if(result.getClass() == String.class){
			return (String)result;
		}else {
			return String.valueOf(result);
		}
	}
	public String receiveFund(String accessToken, String campaignid) throws Exception {
		Object result =  loginService.getUserInfo(accessToken);
		if(result.getClass() == Member.class) {
			Member m = (Member)result;
			Optional<Wallet> wallets = walletRepository.findById(m.getOauthId());
			Optional<InvestmentDto> Invest = investmentRepository.findById(campaignid);
			if(wallets.isPresent()) {
				if(Invest.isPresent()) {
					Wallet mywallet = wallets.get();
					InvestmentDto myInvest = Invest.get();
					Web3j web3j = Web3j.build(new HttpService());
					Credentials credentials = Credentials.create(mywallet.getPrivatekey());
					CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
					contract.receiveFunds(property.getTokenAddr(), String.valueOf(myInvest.getNum())).send();
					System.out.println("receiveFund Success");
					return "success";
				}else {
					return "noInvest";
				}
			}else {
				return "noWallet";
			}
		}else if(result.getClass() == String.class){
			return (String)result;
		}else {
			return String.valueOf(result);
		}
	}
	@Override
	public void reFund(String campaignId) throws Exception {
		Web3j web3j = Web3j.build(new HttpService());
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
		contract.Refund(property.getTokenAddr(), campaignId).send();
		System.out.println("환불 완료");

	}
	@Override
	public void getCam(String campaignId) throws Exception {
		Web3j web3j = Web3j.build(new HttpService());
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());

		//System.out.println(contract.test(property.getTokenAddr(), new BigInteger("100")).send());
		System.out.println(contract.getAddress().send());
		//System.out.println(contract.getSender().send());
		System.out.println(contract.getCampaign(campaignId).send());
		System.out.println(contract.getBalance(property.getTokenAddr()).send());

	}
}
