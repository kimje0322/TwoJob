package com.blocker.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.blocker.request.SaleBoardRequest;

import lombok.Data;

@Entity
@Table(name = "saleboard")
@Data
public class SaleBoardDto {
	@Id
	String address;
	@Column
	String investaddress;
	@Column
	String userid;
	@Column
	String url;
	@Column
	String pjtname;
	@Column
	String startdate;
	@Column
	int saleprice;
	@Column
	String picture;
	@Column
	boolean isfinish;
	@Column(name = "createat", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	String createat;

	public SaleBoardDto() {
	}

	public SaleBoardDto(SaleBoardRequest saleBoardRequest) {
		this.investaddress = saleBoardRequest.getInvestaddress();
		this.userid = saleBoardRequest.getUserid();
		this.url = saleBoardRequest.getUrl();
		this.pjtname = saleBoardRequest.getPjtname();
		this.startdate = saleBoardRequest.getStartdate();
		this.saleprice = saleBoardRequest.getSaleprice();
		this.picture = saleBoardRequest.getPicture();
	}

	public SaleBoardDto(SaleBoardDto saleBoardDto) {
		this.address = saleBoardDto.getAddress();
		this.investaddress = saleBoardDto.getInvestaddress();
		this.userid = saleBoardDto.getUserid();
		this.url = saleBoardDto.getUrl();
		this.pjtname = saleBoardDto.getPjtname();
		this.startdate = saleBoardDto.getStartdate();
		this.saleprice = saleBoardDto.getSaleprice();
		this.picture = saleBoardDto.getPicture();
	}

}
