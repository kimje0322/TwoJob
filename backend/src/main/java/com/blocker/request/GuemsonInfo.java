package com.blocker.request;

import lombok.Data;

@Data
public class GuemsonInfo {
	String identity;// 개인인지 사업자인지
	String compName;// 회사명
	String introduce;// 금손소개
	String url;// 참고할 url
}
