package com.blocker.request;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ProjectInfoRequest {
	String pjtName;// 플젝명
	String oneLineIntro;// 한줄소개
	Date deadLine;// 마감날짜
	int goalPrice;// 달성금액
	int expectedSalePrice;// 판매 예정금액
	String pictual;// 대표사진이름
	List<String> categorys;// 카테고리
	List<String> tags;
}
