package com.blocker.service;

public interface LoginService {
	public String getAccessToken (String code);
	public String getUserInfo (String access_Token);
}
