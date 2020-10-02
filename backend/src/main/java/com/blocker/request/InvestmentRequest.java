package com.blocker.request;

import java.util.List;

import com.blocker.dto.CommentBoardDto;

import lombok.Data;

@Data
public class InvestmentRequest {
	String userid;
	String identity;// 개인인지 사업자인지
	String compName;// 회사명
	String introduce;// 금손소개
	String url;// 소개 사이트
	String pjtName;// 플젝명
	String oneLineIntro;// 한줄소개
	String deadLine;// 마감날짜
	int goalPrice;// 달성금액
	int expectedSalePrice;// 판매 예정금액
	String picture;// 대표사진이름
	List<String> categorys;// 카테고리
	List<String> tags;// 테그
	List<CommentBoardDto> comments;// 댓글들
	String editorhtml;

	public InvestmentRequest() {

	}

	public InvestmentRequest(List<String> pcategorys, List<String> ptags, List<CommentBoardDto> pcomments) {
		this.categorys = pcategorys;
		this.tags = ptags;
		this.comments = pcomments;
	}
}
