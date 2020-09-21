package com.blocker.service;

public interface LoginService {
	public Object getUserInfo (String access_Token);
	public String Logout(String accessToken);
}
