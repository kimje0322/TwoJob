package com.blocker.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.blocker.dto.receipt;

public interface FundingService {
	public void Deploy() throws Exception;
	public String createCampaign(String accessToken, String id) throws Exception;
	public String fundingCampaign(String accessToken, String campaignid, String value) throws Exception;
	public String receiveFund(String campaignid) throws Exception;
	public String reFund(String campaignId) throws Exception;
	public void getCam(String campaignId) throws Exception;
	public String getPepleNum(String campaignId) throws Exception;
	public String sellItem(String accessToken,String campaignId, Integer cnt, Integer money) throws Exception;
	public BigDecimal getfundingrate(String campaignId) throws Exception;
	public void makeAllTask() throws Exception;
	public Map<String, String> getMyProject(String oauthId) throws Exception;
	public String getNowRaised(String campaignId) throws Exception;
	public String createSale(String accessToken, String id) throws Exception;
	public List<String> getReceipt(String campaignId) throws Exception;
	public String getProjectState(String campaignId) throws Exception;
	public String useFund(String accessToken, String campaignId,List<receipt> list) throws Exception;
	public String getTotalSell(String campainId) throws Exception;
}
