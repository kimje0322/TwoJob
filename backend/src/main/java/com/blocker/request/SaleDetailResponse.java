package com.blocker.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.blocker.dto.ReviewDto;
import com.blocker.dto.SaleBoardDto;

import lombok.Data;

@Data
public class SaleDetailResponse {
	SaleBoardDto saleBoardDto;
	List<ReviewDto> reviews;

	public SaleDetailResponse(SaleBoardDto psalebordDto, List<ReviewDto> list) {
		this.saleBoardDto = new SaleBoardDto();
		this.saleBoardDto.setAddress(psalebordDto.getAddress());
		this.saleBoardDto.setInvestaddress(psalebordDto.getInvestaddress());
		this.saleBoardDto.setPicture(psalebordDto.getPicture());
		this.saleBoardDto.setPjtname(psalebordDto.getPjtname());
		this.saleBoardDto.setSaleprice(psalebordDto.getSaleprice());
		this.saleBoardDto.setStartdate(psalebordDto.getStartdate());
		this.saleBoardDto.setUrl(psalebordDto.getUrl());
		this.saleBoardDto.setUserid(psalebordDto.getUserid());

		this.reviews = list;
	}
}
