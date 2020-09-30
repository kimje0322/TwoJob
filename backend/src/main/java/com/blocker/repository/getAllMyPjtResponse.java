package com.blocker.repository;

import java.util.List;

import com.blocker.dto.InvestmentDto;
import com.blocker.dto.SaleBoardDto;

import lombok.Data;

@Data
public class getAllMyPjtResponse {
	InvestmentDto investmentDto;
	SaleBoardDto saleBoardDto;
	int totalpages;

	public getAllMyPjtResponse() {

	}

	public getAllMyPjtResponse(InvestmentDto pinvestmentDto, SaleBoardDto psaleBoardDto, int ptotalPages) {
		this.investmentDto = pinvestmentDto;
		this.saleBoardDto = psaleBoardDto;
		this.totalpages = ptotalPages;
	}
	
	public getAllMyPjtResponse(InvestmentDto pinvestmentDto,  int ptotalPages) {
		this.investmentDto = pinvestmentDto;
		this.totalpages = ptotalPages;
	}
}
