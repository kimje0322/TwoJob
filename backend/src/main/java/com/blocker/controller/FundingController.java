package com.blocker.controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blocker.dto.Chatroom;
import com.blocker.dto.TransactType;
import com.blocker.dto.receipt;
import com.blocker.dto.useFundDTO;
import com.blocker.repository.BlockTransactionRepository;
import com.blocker.service.FundingService;
import com.blocker.service.ScheduleTask;
import com.blocker.util.BasicResponse;
import com.blocker.util.webhook;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/funding")
public class FundingController {
	@Autowired
	FundingService fundingService;
	@Autowired
	BlockTransactionRepository blockTransactionRepository;
	@ApiOperation(value = "funding deploy하기, 건들지 마십숑")
	@PostMapping(value = "/deploy")
	public void Deploy() throws Exception {
		fundingService.Deploy();
	}
	@ApiOperation(value = "[BC][펀딩 캠페인 오픈] 펀딩받을 캠페인을 블록체인에 등록합니다. param : [accessToken, campaignId], return : 성공시 success, 지갑이 없을경우 noWallet, 캠페인이 없을 경우 noInvest")
	@PostMapping(value = "/createfunding")
	public ResponseEntity<String> createFunding(@RequestParam("accessToken") String accessToken, @RequestParam("canpaignId") String campaignId) throws Exception {
		return new ResponseEntity<String>(fundingService.createCampaign(accessToken,campaignId),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][펀딩 받기] 토큰을 이용해 원하는 캠페인에 펀딩합니다. param : [accessToken, campaignId, value(토큰값)], return : 성공시 success")
	@PostMapping(value = "/tofunding")
	public ResponseEntity<String> toFunding(@RequestParam("accessToken") String accessToken, @RequestParam("campaignId") String campaignId, @RequestParam("Value") String value) throws Exception {
		return new ResponseEntity<String>(fundingService.fundingCampaign(accessToken, campaignId, value),HttpStatus.OK);
	}

	@ApiOperation(value = "[BC][펀딩 금액 받기] 펀딩이 목표에 도달 했을 경우, 펀딩된 금액을 받습니다. param : [campaignId], return : 성공시 success, 캠페인이 없을 경우 noInvest")
	@PostMapping(value = "/receivefund")
	public ResponseEntity<String> receiveFund(@RequestParam("campaignId") String campaignId) throws Exception {
		return new ResponseEntity<String>(fundingService.receiveFund(campaignId),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][펀딩 금액 환불] 펀딩이 목표에 도달 못 했을 경우, 투자자들에게 투자했던 금액을 돌려줍니다. param : [campaignId], return : ")
	@GetMapping(value = "/refund")
	public ResponseEntity<Void> refund(@RequestParam("campaignId") String campaignId) throws Exception {
		fundingService.reFund(campaignId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][펀딩 정보 검색 ] test용임 건들지 마세용 ")
	@GetMapping(value = "/test")
	public ResponseEntity<Void> transfer3(String campaignId) throws Exception {
		fundingService.getCam(campaignId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][해당 투자에 펀딩한 사람의 수] 투자 아이디를 주면, 현재 해당 투자에 펀딩한 사람의 수를 return해줍니다. param : [campaignId], return : 펀딩한 사람의 수 ")
	@GetMapping(value = "/fundernum")
	public ResponseEntity<String> FunderNum(String campaignId) throws Exception {
		return new ResponseEntity<String>(fundingService.getPepleNum(campaignId),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][해당 투자에 모인 금액확인] 투자 아이디를 주면, 현재 해당 투자에 모인 금액을 리턴합니다.. param : [campaignId], return : 해당 투자 모금 금액 ")
	@GetMapping(value = "/nowfund")
	public ResponseEntity<String> nowRaised(String campaignId) throws Exception {
		return new ResponseEntity<String>(fundingService.getNowRaised(campaignId),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][판매 프로젝트 오픈] ")
	@PostMapping(value = "/sellopen")
	public ResponseEntity<String> SellOpen(@RequestParam("accessToken") String accessToken, @RequestParam("campaignId") String campaignId) throws Exception {
		return new ResponseEntity<String>(fundingService.createSale(accessToken, campaignId),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][판매용] ")
	@PostMapping(value = "/sellitem")
	public ResponseEntity<String> SellItems(@RequestParam("accessToken") String accessToken, @RequestParam("campaignId") String campaignId, @RequestParam("count") Integer cnt, @RequestParam("money") Integer money) throws Exception {
		return new ResponseEntity<String>(fundingService.sellItem(accessToken, campaignId, cnt, money),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][펀딩 달성률] 투자 아이디를 주면, 현재 해당 투자에 펀딩한 사람의 수를 return해줍니다. param : [campaignId], return : 펀딩한 사람의 수 ")
	@PostMapping(value = "/fundingrate")
	public ResponseEntity<BigDecimal> fundingrate( @RequestParam("campaignId") String campaignId) throws Exception {
		return new ResponseEntity<BigDecimal>(fundingService.getfundingrate(campaignId),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][해당 투자 진행 상태] 투자 아이디를 주면, 현재 해당 투자 진행 상태를 리턴합니다. param : [campaignId], return : 해당 투자 진행 상태 ")
	@GetMapping(value = "/status")
	public ResponseEntity<String> projectStatus(String campaignId) throws Exception {
		return new ResponseEntity<String>(fundingService.getProjectState(campaignId),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][투자 금액 사용 내역] 투자 금액 사용 내역을 블록에 기록합니다. param : [accessToken, campaignId, imgname, value], return : 성공 시 success ")
	@PostMapping(value = "/usefund")
	public ResponseEntity<String> usefund(@RequestBody useFundDTO useFund) throws Exception {
		String accessToken = useFund.getAcceesToken();
		String campaignId = useFund.getCampaignId();
		List<receipt> list = useFund.getList();
		return new ResponseEntity<String>(fundingService.useFund(accessToken, campaignId, list),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][해당 투자 총 판매 개수] 투자 아이디를 주면, 현재 해당 투자의 총 판매 개수를 가져옴 param : [campaignId], return : 해당 투자 총 판매 개수 ")
	@GetMapping(value = "/gettotalsell")
	public ResponseEntity<String> getTotalSell(String campaignId) throws Exception {
		return new ResponseEntity<String>(fundingService.getTotalSell(campaignId),HttpStatus.OK);
	}
	@ApiOperation(value = "[BC][해당 투자 영수증 얻긴] 투자 아이디를 주면, 현재 해당 투자 영수증을 가져옴 param : [campaignId], return : 해당 투자 영수증 img 이름 들 ")
	@GetMapping(value = "/getreceipt")
	public ResponseEntity<List<String>> getReceipt(String campaignId) throws Exception {
		return new ResponseEntity<List<String>>(fundingService.getReceipt(campaignId),HttpStatus.OK);
	}
	@ApiOperation(value = "testg ")
	@PostMapping(value = "/testd")
	public void test() {
		
	}

	//	@ExceptionHandler(Exception.class)
	//	public void nullex(Exception e) {
	//		StringWriter errors = new StringWriter();
	//        e.printStackTrace(new PrintWriter(errors));
	//        String result = errors.toString();
	//		System.err.println("Funding 부분에서 " + e.getClass());
	//		System.out.println(result);
	//		webhook w = new webhook();
	//		w.send("Funding 부분에서 " + e.getClass());
	//		//w.postHttpsRequest(result, e.getClass().toString());
	//	}
}
