package com.blocker.service;

public interface FundingService {
	public void Deploy();
	public String createCampaign(String accessToken);
	public void fundingCampaign();
	public void getCam();
	public void receiveFund();
	public void reFund();
}
