package com.blocker.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import com.blocker.dto.Property;
import com.blocker.wrapper.CrowdFunding;
import com.blocker.wrapper.TJToken;

@Service
public class FundingServiceImpl implements FundingService{
	
	@Autowired
	Property property;
	
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
	public void createCampaign() {
		Web3j web3j = Web3j.build(new HttpService());
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
		try {
			contract.createCampaign("123", Convert.toWei(String.valueOf(5), Convert.Unit.ETHER).toBigInteger(), new BigInteger("123123123123")).send();
			System.out.println("dddddddd");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void fundingCampaign() {
		Web3j web3j = Web3j.build(new HttpService());
		Credentials credentials = Credentials.create("b08800e0672fbcddce24e16545e69579b9a2efef7df77bdc85fd13edbae60f54");
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
		try {
			System.out.println("후 = " +contract.isValid());
			contract.FundingCampign(property.getTokenAddr(),credentials.getAddress(), "123", Convert.toWei(String.valueOf(2), Convert.Unit.ETHER).toBigInteger()).send();
			System.out.println("dddddddd123");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void receiveFund() {
		Web3j web3j = Web3j.build(new HttpService());
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
		try {
			contract.receiveFunds(property.getTokenAddr(), "123").send();
			System.out.println("여기요");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void reFund() {
		Web3j web3j = Web3j.build(new HttpService());
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
		try {
			contract.Refund(property.getTokenAddr(), "123").send();
			System.out.println("여기요22");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getCam() {
		Web3j web3j = Web3j.build(new HttpService());
		Credentials credentials = Credentials.create("127aef0f773889678daea871a71322886840988b5a96048fc5b01dd4ae4aa2d4");
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
		try {
			//System.out.println(contract.test(property.getTokenAddr(), new BigInteger("100")).send());
			System.out.println(contract.getAddress().send());
			//System.out.println(contract.getSender().send());
			System.out.println(contract.getCampaign("123").send());
			System.out.println(contract.getBalance(property.getTokenAddr()).send());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
