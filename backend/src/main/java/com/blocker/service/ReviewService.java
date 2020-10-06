package com.blocker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blocker.dto.ReviewDto;
import com.blocker.repository.ReviewRepository;
import com.blocker.repository.SaleBoardRepository;

@Service
public class ReviewService {

	@Autowired
	SaleBoardRepository saleBoardRepository;
	@Autowired
	ReviewRepository reviewRepository;

	public ReviewDto createReview(ReviewDto reviewDto) {
		return reviewRepository.save(reviewDto);
	}

	public Page<ReviewDto> getReviews(String saleaddress, int page) {
		PageRequest pageRequest = PageRequest.of(page, 10);
		return reviewRepository.findAllBySaleaddress(pageRequest, saleaddress);
	}

	public List<ReviewDto> getReviews(String saleaddress) {
		return reviewRepository.findAllBySaleaddress(saleaddress);
	}
	
	public int getReviewsCount(String saleaddress) {
		return reviewRepository.getCountAllReview(saleaddress);
	}
}
