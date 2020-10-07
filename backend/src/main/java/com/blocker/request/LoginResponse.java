package com.blocker.request;

import com.blocker.dto.Member;

import lombok.Data;

@Data
public class LoginResponse {
	Member member;
	boolean isfirsttime;

	public LoginResponse() {
	}

	public LoginResponse(Member pmember) {
		this.member = pmember;
	}
}
