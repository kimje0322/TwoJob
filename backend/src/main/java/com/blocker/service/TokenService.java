package com.blocker.service;

public interface TokenService {
	public void Deploy() throws Exception;
	public String getTotal() throws Exception;
	public String getTokenNum(String address) throws Exception;
	public String TransferTo(String to,Integer amount) throws Exception;
	public void Transferfrm(String from, String to, Integer amount) throws Exception;
	public String getAdminVal() throws Exception;
	public String IncreaseToken(Integer amount) throws Exception;
}
