package com.blocker.request;

import java.sql.Date;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
public class SaleBoardReviewRequest {
	String saleaddress;
	int satisfied;
	int similar;
	String reviewPicture;
	String reviewexplain;
	String userid;
}
