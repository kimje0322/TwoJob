package com.blocker.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "investmentboard")
public class InvestmentDto{
	@Id
	String address;

	@Column
	String userid;// 유저아이디

	@Column
	String identity;// 개인인지 사업자인지

	@Column
	String compname;// 회사명

	@Column
	String introduce;// 금손소개

	@Column
	String url;// 참고할 url

	@Column
	String pjtname;// 플젝명

	@Column
	String onelineintro;// 한줄소개

	@Column
	String deadline;// 마감날짜

	@Column
	int goalprice;// 달성금액

	@Column
	int expectedsaleprice;// 판매 예정금액

	@Column
	boolean isfinish;

	@Column
	String picture;// 대표사진이름
	
	@Column(name = "createat", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	String createdat;

}
