package com.blocker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blocker.dto.InvestmentDto;
import com.blocker.repository.InvestmentRepository;

@Service
public class InvestmentService {

	@Autowired
	InvestmentRepository investmentRepository;

	public Page<InvestmentDto> getAllMyinvestmentList(String userid, int page) {
		PageRequest pageRequest = PageRequest.of(page, 9);
		return investmentRepository.findAllByUserid(pageRequest, userid);
	}
	
	public Page<InvestmentDto> getAllMyinvestmentListForGetAllPJT(String userid, int page) {
		PageRequest pageRequest = PageRequest.of(page, 3);
		return investmentRepository.findAllByUserid(pageRequest, userid);
	}

	public Page<InvestmentDto> getAllInvestmentList(int page) {
		PageRequest pageRequest = PageRequest.of(page, 9);

		return investmentRepository.findAll(pageRequest);
	}

	public Optional<InvestmentDto> getInvestment(String address) {
		return investmentRepository.findInvestmentDtoByAddress(address);
	}

	public Page<InvestmentDto> getAllInvestmentList(int page, int orderOption) {
		PageRequest pagerequest = PageRequest.of(page, 9);
		// 최신순
		if (orderOption == 1) {
			return investmentRepository.findAllInvestmentDtoOrderbyCreatedat(pagerequest);
		} else {//인기순
			return investmentRepository.findAllInvestmentDtoOrderbyLikecount(pagerequest);
		}
	}
	
	public List<InvestmentDto> getThreeInvestmentListOrderbyDeadline(){
		return investmentRepository.findThreeInvestmentboardOrderbyDeadline();
	}
	
	public List<InvestmentDto> getThreeInvestmentListOrderbyLikecount(){
		return investmentRepository.findThreeInvestmentboardOrderbyLikecount();
	}

}
