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

	public Page<BoardCategoryMapping> getAllInvestmentListWithCategory(int page, List<String> category,
			int orderOption) {
		PageRequest pageRequest = PageRequest.of(page, 9);

		if (orderOption == 0) {
			return boardCategoryRepository.findAllInvestmentDtoWithCategory(category, category.size(), pageRequest);
		}
		else if(orderOption==1) {
			return boardCategoryRepository.findAllInvestmentDtoWithCategoryOrderbycreateat(category, category.size(), pageRequest);
		}else {
			return boardCategoryRepository.findAllInvestmentDtoWithCategoryOrderbyLikeCount(category, category.size(), pageRequest);
		}
	}
	
//	public Page<BoardCategoryMapping> getAllSaleBoardListWithCategory(int page, List<String> category,int orderOption){
//		PageRequest pageRequest = PageRequest.of(page, 9);
//		
//		if(orderOption ==0) {
//			return 
//		}else if(orderOption==1) {
//			return
//		}else {
//			return
//		}
//	}
}
