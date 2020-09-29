package com.blocker.service;

import java.math.BigDecimal;

public interface FundingService {
	public void Deploy() throws Exception;
	public String createCampaign(String accessToken, String id) throws Exception;
	public String fundingCampaign(String accessToken, String campaignid, String value) throws Exception;
	public String receiveFund(String accessToken, String campaignid) throws Exception;
	public void reFund(String campaignId) throws Exception;
	public void getCam(String campaignId) throws Exception;
	public String getPepleNum(String campaignId) throws Exception;
	public String sellItem(String accessToken,String campaignId, int cnt, int money) throws Exception;
	public BigDecimal getfundingrate(String campaignId) throws Exception;
}
