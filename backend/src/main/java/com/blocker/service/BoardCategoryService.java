package com.blocker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blocker.repository.BoardCategoryMapping;
import com.blocker.repository.BoardCategoryRepository;

@Service
public class BoardCategoryService {
 @Autowired
 BoardCategoryRepository boardCategoryRepository;
 
 public Page<BoardCategoryMapping> getAllInvestmentListWithCategory(int page, List<String> category) {
		PageRequest pageRequest = PageRequest.of(page, 9);
		
		return boardCategoryRepository.findAllInvestmentDtoWithCategory(category, category.size(), pageRequest);
	}
}
