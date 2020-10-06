package com.blocker.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.Tuple;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import com.blocker.dto.BlockTransaction;
import com.blocker.dto.InvestmentDto;
import com.blocker.dto.Member;
import com.blocker.dto.Property;
import com.blocker.dto.SaleBoardDto;
import com.blocker.dto.TransactType;
import com.blocker.dto.Wallet;
import com.blocker.dto.receipt;
import com.blocker.repository.BlockTransactionRepository;
import com.blocker.repository.InvestmentRepository;
import com.blocker.repository.MemberRepository;
import com.blocker.repository.SaleBoardRepository;
import com.blocker.repository.WalletRepository;
import com.blocker.wrapper.CrowdFunding;

@Service
public class FundingServiceImpl implements FundingService{

	@Autowired
	Property property;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	LoginService loginService;
	@Autowired
	WalletRepository walletRepository;
	@Autowired
	InvestmentRepository investmentRepository;
	@Autowired
	SaleBoardRepository saleBoardRepository;
	@Autowired
	BlockTransactionRepository blockTransactionRepository;
	@Autowired
	TokenService tokenService;
	@Override
	public void Deploy() throws Exception {
		Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = null;
		contract = CrowdFunding.deploy(web3j, credentials, new DefaultGasProvider()).send();
		System.out.println(contract.isValid());
		System.out.println(contract.getContractAddress());
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
					Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
					Credentials credentials = Credentials.create(mywallet.getPrivatekey());
					CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
					TransactionReceipt tr = contract.createCampaign(String.valueOf(myInvest.getAddress()), Convert.toWei(String.valueOf(myInvest.getGoalprice()), Convert.Unit.ETHER).toBigInteger()).send();
					System.out.println("캠페인 생성 완료!");
					BlockTransaction transact = new BlockTransaction(tr.getBlockHash(),myInvest.getAddress(),myInvest.getCompname(),tr.getFrom(),myInvest.getPicture(),Double.valueOf("0"),TransactType.CREATE,1);
					blockTransactionRepository.save(transact);
					makeTask(myInvest);
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
					Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
					Credentials credentials = Credentials.create(mywallet.getPrivatekey());
					CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
					String val = tokenService.getTokenNum(accessToken);
					if(Long.valueOf(val) < Long.valueOf(value)) {
						return "보유토큰부족";
					}
					System.out.println("후 = " +contract.isValid());
					System.out.println("주소? " + myInvest.getAddress());
					TransactionReceipt tr =contract.FundingCampign(property.getTokenAddr(),credentials.getAddress(), String.valueOf(myInvest.getAddress()), Convert.toWei(value, Convert.Unit.ETHER).toBigInteger()).send();
					BlockTransaction transact = new BlockTransaction(tr.getBlockHash(),myInvest.getAddress(),myInvest.getCompname(),tr.getFrom(),myInvest.getPicture(),Double.valueOf(value),TransactType.FUND,1);
					blockTransactionRepository.save(transact);
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
	public String receiveFund(String campaignid) throws Exception {
		Optional<InvestmentDto> Invest = investmentRepository.findById(campaignid);
		if(Invest.isPresent()) {
			InvestmentDto myInvest = Invest.get();
			Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
			Credentials credentials = Credentials.create(property.getAdminPK());
			CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
			TransactionReceipt tr = contract.receiveFunds(property.getTokenAddr(), String.valueOf(myInvest.getAddress())).send();
			Optional<Wallet> mywallet = walletRepository.findByAddress(contract.getCampaign(campaignid,property.getAdminAddr()).send().component2());
			Optional<Member> m =memberRepository.findById(mywallet.get().getOauthId());
			List<BlockTransaction> list = blockTransactionRepository.findByToaddressAndType(myInvest.getAddress(),TransactType.FUND);
			long value = 0;
			for(int i=0; i<list.size(); i++) {
				value+= list.get(i).getValue();
			}
			BlockTransaction transact = new BlockTransaction(tr.getBlockHash(),myInvest.getAddress(),myInvest.getCompname(),myInvest.getPicture(),mywallet.get().getAddress(),Double.valueOf(value),TransactType.RECEIVE,1);
			blockTransactionRepository.save(transact);
			System.out.println("receiveFund Success");
			return "success";
		}else {
			return "noInvest";
		}
	}
	@Override
	public String reFund(String campaignId) throws Exception {
		Optional<InvestmentDto> Invest = investmentRepository.findById(campaignId);
		if(Invest.isPresent()) {
			InvestmentDto myInvest = Invest.get();
			Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
			Credentials credentials = Credentials.create(property.getAdminPK());
			CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
			TransactionReceipt tr = contract.Refund(property.getTokenAddr(), campaignId).send();
			List<BlockTransaction> list = blockTransactionRepository.findByToaddressAndType(myInvest.getAddress(),TransactType.FUND);
			for(int i=0; i<list.size(); i++) {
				String address = list.get(i).getFromaddress();
				Optional<Wallet> mywallet = walletRepository.findByAddress(address);
				Optional<Member> m =memberRepository.findById(mywallet.get().getOauthId());
				Double value = list.get(i).getValue();
				BlockTransaction transact = new BlockTransaction(tr.getBlockHash(),myInvest.getAddress(),myInvest.getCompname(),myInvest.getPicture(),mywallet.get().getAddress(),value,TransactType.REFUND,1);
				blockTransactionRepository.save(transact);
			}
			System.out.println("환불 완료");
			return "success";
		}else {
			return "noInvest";
		}
	}

	@Override
	public String sellItem(String accessToken,String campaignId, Integer cnt, Integer money) throws Exception {
		Object result =  loginService.getUserInfo(accessToken);
		if(result.getClass() == Member.class) {
			Member m = (Member)result;
			Optional<Wallet> wallets = walletRepository.findById(m.getOauthId());
			if(wallets.isPresent()) {
				Optional<InvestmentDto> invest = investmentRepository.findById(campaignId);
				if(invest.isPresent()) {
					String myToken = tokenService.getTokenNum(accessToken);
					if(Long.valueOf(myToken) < cnt*money) {
						return "보유토큰부족";
					}
					InvestmentDto myInvest = invest.get();
					Wallet mywallet = wallets.get();
					Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
					Credentials credentials = Credentials.create(property.getAdminPK());
					CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());		
					TransactionReceipt tr = contract.SaleItem(property.getTokenAddr(), campaignId, mywallet.getAddress(),BigInteger.valueOf(cnt), BigInteger.valueOf(money)).send();
					Double val = (double)(cnt*money)/2;
					Double value = Math.round((val) * 100) / 100.0;
					BlockTransaction transact = new BlockTransaction(tr.getBlockHash(),myInvest.getAddress(),myInvest.getCompname(),mywallet.getAddress(),myInvest.getPicture(),value,TransactType.SALE,2);
					blockTransactionRepository.save(transact);
					List<BlockTransaction> list = blockTransactionRepository.findDistinctFromAddressByToaddressAndType(myInvest.getAddress(),TransactType.FUND);
					for(int i=0; i<list.size(); i++) {
						String address = list.get(i).getFromaddress();
						Optional<Wallet> wallet2 = walletRepository.findByAddress(address);
						Optional<Member> m2 =memberRepository.findById(wallet2.get().getOauthId());
						BigInteger fundraise = contract.getCampaign(address, property.getAdminAddr()).send().component4();
						BigInteger investraise = contract.getCampaign(address, property.getAdminAddr()).send().component5();
						BigDecimal dfundraise = new BigDecimal(fundraise);
						BigDecimal dinvestraise = new BigDecimal(investraise);
						BigDecimal resultval = new BigDecimal("0");
						if(dfundraise.equals(new BigDecimal("0"))) {
							resultval = new BigDecimal("0");
						}else {
							resultval = dinvestraise.divide(dfundraise,3, BigDecimal.ROUND_HALF_UP);
						}
						BigDecimal myval = BigDecimal.valueOf(value).multiply(resultval);
						BlockTransaction transact2 = new BlockTransaction(tr.getBlockHash(),myInvest.getAddress(),myInvest.getCompname(),myInvest.getPicture(),wallet2.get().getAddress(),myval.doubleValue(),TransactType.SALEPARTIN,2);
						blockTransactionRepository.save(transact2);
					}
					System.out.println("판매완료!");
					return "scucess";
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
	public BigDecimal getfundingrate(String campaignId) throws Exception {
		Optional<InvestmentDto> invest = investmentRepository.findById(campaignId);
		if(invest.isPresent())
		{
			Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
			Credentials credentials = Credentials.create(property.getAdminPK());
			CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
			BigInteger fundingGoal = contract.getCampaign(campaignId,property.getAdminAddr()).send().component3();
			BigInteger fundingval = contract.getCampaign(campaignId,property.getAdminAddr()).send().component4();
			BigDecimal dfundingval = new BigDecimal(fundingval);
			BigDecimal dfundingGoal = new BigDecimal(fundingGoal);
			if(dfundingGoal.equals(new BigDecimal("0"))) {
				return new BigDecimal("0");
			}
			BigDecimal result = dfundingval.divide(dfundingGoal,3, BigDecimal.ROUND_HALF_UP);
			BigDecimal perResult = result.multiply(new BigDecimal(100));
			return perResult;
		}else {
			return new BigDecimal("0");
		}
	}
	@Override
	public void getCam(String campaignId) throws Exception {
		Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());

		//System.out.println(contract.test(property.getTokenAddr(), new BigInteger("100")).send());
		//System.out.println(contract.getAddress().send());
		//System.out.println(contract.getSender().send());
		System.out.println(contract.getCampaign(campaignId,property.getAdminAddr()).send());
		System.out.println(contract.getBalance(property.getTokenAddr()).send());
	}
	@Override
	public String createSale(String accessToken, String id) throws Exception {
		Object result =  loginService.getUserInfo(accessToken);
		if(result.getClass() == Member.class) {
			Member m = (Member)result;
			Optional<Wallet> wallets = walletRepository.findById(m.getOauthId());
			Optional<SaleBoardDto> Invest = saleBoardRepository.findById(id);
			if(wallets.isPresent()) {
				if(Invest.isPresent()) {
					Wallet mywallet = wallets.get();
					SaleBoardDto myInvest = Invest.get();
					Optional<InvestmentDto> Invest1 = investmentRepository.findById(myInvest.getInvestaddress());
					Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
					Credentials credentials = Credentials.create(mywallet.getPrivatekey());
					CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
					TransactionReceipt tr = contract.createSale(myInvest.getInvestaddress(), BigInteger.valueOf(myInvest.getSaleprice()), myInvest.getPicture()).send();
					System.out.println("캠페인 생성 완료!");
					BlockTransaction transact = new BlockTransaction(tr.getBlockHash(),Invest1.get().getAddress(),Invest1.get().getPjtname(),tr.getFrom(),myInvest.getPicture(),Double.valueOf("0"),TransactType.SALEOPEN,2);
					blockTransactionRepository.save(transact);
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
	public String getPepleNum(String campaignId) throws Exception {
		Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
		String val = String.valueOf(contract.getPeopleNum(campaignId).send());
		System.out.println(val);
		return val;
	}
	@Override
	public String getNowRaised(String campaignId) throws Exception {
		Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
		String val = String.valueOf(contract.getCampaign(campaignId, property.getAdminAddr()).send().component4());
		String real = String.valueOf(Convert.fromWei(val, Convert.Unit.ETHER));
		System.out.println(real);
		return real;
	}
	@Override
	public Map<String, String> getMyProject(String oauthId) throws Exception{
		Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
		Map<String, String> result = new HashMap<String, String>();
		Optional<Wallet> mywallet = walletRepository.findById(oauthId);
		if(mywallet.isPresent()) {
			String address = mywallet.get().getAddress();
			BigInteger createNum = contract.getInfo(address).send().component1();
			BigInteger partInNum = contract.getInfo(address).send().component2();
			BigInteger saleNum = contract.getInfo(address).send().component3();
			BigInteger partInsaleNum = contract.getInfo(address).send().component4();
			result.put("createNum", String.valueOf(createNum));
			result.put("partInNum", String.valueOf(partInNum));
			result.put("saleNum", String.valueOf(saleNum));
			result.put("partInSaleNum", String.valueOf(partInsaleNum));
		}
		return result;
	}
	@Override
	public String getProjectState(String campaignId) throws Exception {
		Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
		String val = String.valueOf(contract.getCampaign(campaignId, property.getAdminAddr()).send().component6());
		switch(val) {
		case "0":
			val = "Ready";
			break;
		case "1":
			val = "Open";
			break;
		case "2":
			val = "Close";
			break;
		case "3":
			val = "Sell";
			break;
		case "4":
			val = "SellClose";
			break;
		}
		System.out.println(val);
		return val;
	}
	@Override
	public List<String> getReceipt(String campaignId) throws Exception {
		Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
		List<String> list = contract.getReceiptImg(campaignId).send();
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
		return list;
	}
	@Override
	public String useFund(String accessToken, String campaignId,List<receipt> list) throws Exception {
		Object result =  loginService.getUserInfo(accessToken);
		if(result.getClass() == Member.class) {
			Member m = (Member)result;
			Optional<Wallet> wallets = walletRepository.findById(m.getOauthId());
			Optional<InvestmentDto> Invest = investmentRepository.findById(campaignId);
			if(wallets.isPresent()) {
				if(Invest.isPresent()) {
					Wallet mywallet = wallets.get();
					InvestmentDto myInvest = Invest.get();
					Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
					Credentials credentials = Credentials.create(mywallet.getPrivatekey());
					CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
					for(int i=0; i<list.size(); i++) {
						contract.usefund(campaignId, list.get(i).getImg(), BigInteger.valueOf(list.get(i).getPrice())).send();
					}
					return "scucess";
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
	public String getTotalSell(String campainId) throws Exception {
		Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());
		return String.valueOf(contract.getTotalSell(campainId).send());
	}
	
	public void makeAllTask() throws Exception {
		Web3j web3j = Web3j.build(new HttpService("http://j3b102.p.ssafy.io:8545"));
		Credentials credentials = Credentials.create(property.getAdminPK());
		CrowdFunding contract = CrowdFunding.load(property.getFundingAddr(), web3j, credentials, new DefaultGasProvider());

		ExecutorService service = Executors.newFixedThreadPool(1);
		List<InvestmentDto> list = investmentRepository.findByIsfinishOrderByDeadlineAsc(false);
		for(int i=0; i<list.size(); i++) {
			BigInteger val = contract.getCampaign(list.get(i).getAddress(), property.getAdminAddr()).send().component6();
			if(val.equals(new BigInteger("1"))) {
				service.submit(makeTask(list.get(i)));
			}
		}
	}
	public ScheduleTask makeTask(InvestmentDto invest) {
		String dead = invest.getDeadline();
		String year = dead.split("/")[0];
		String month = dead.split("/")[1];
		String day = dead.split("/")[2];
		month = month.charAt(0)=='0'?month.substring(1,2):month;
		day = day.charAt(0)=='0'?day.substring(1,2):day;
		ScheduleTask task = new ScheduleTask(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), invest.getAddress());
		//task.start();
		System.out.println("task 생성완료!");
		return task;
	}
}
