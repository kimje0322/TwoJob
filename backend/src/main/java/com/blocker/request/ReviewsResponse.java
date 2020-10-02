package com.blocker.request;

import java.util.List;

import com.blocker.dto.ReviewDto;

import lombok.Data;

@Data
public class ReviewsResponse {
	List<ReviewDto> list;
	int totalPage;

	public ReviewsResponse(List<ReviewDto> plist, int ptotalPage) {
		this.list = plist;
		this.totalPage = ptotalPage;
	}

	public ReviewsResponse() {
	}
}
