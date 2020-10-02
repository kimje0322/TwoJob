package com.blocker.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.blocker.dto.EditorSaleDto;
import com.blocker.dto.ReviewDto;
import com.blocker.dto.SaleBoardDto;

import lombok.Data;

@Data
public class SaleDetailResponse {
	SaleBoardDto saleBoardDto;
	String compname;
	String onelineIntro;
	String url;
	String introduce;
	String editorhtml;
	List<String> tags;
	List<String> categorys;

	public SaleDetailResponse(SaleBoardDto psalebordDto, String pcompname, String ponelineIntro,
			EditorSaleDto editorSaleDto, List<String> ptags, List<String> pcategorys) {
		this.saleBoardDto = new SaleBoardDto();
		this.saleBoardDto.setAddress(psalebordDto.getAddress());
		this.saleBoardDto.setInvestaddress(psalebordDto.getInvestaddress());
		this.saleBoardDto.setPicture(psalebordDto.getPicture());
		this.saleBoardDto.setPjtname(psalebordDto.getPjtname());
		this.saleBoardDto.setSaleprice(psalebordDto.getSaleprice());
		this.saleBoardDto.setStartdate(psalebordDto.getStartdate());
		this.saleBoardDto.setUrl(psalebordDto.getUrl());
		this.saleBoardDto.setUserid(psalebordDto.getUserid());
		this.editorhtml = editorSaleDto.getEditorhtml();
		this.compname = pcompname;
		this.onelineIntro = ponelineIntro;
		tags = ptags;
		categorys = pcategorys;
	}
}
