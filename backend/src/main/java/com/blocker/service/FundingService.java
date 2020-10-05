package com.blocker.service;

import java.math.BigDecimal;
import java.util.Map;

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
}
