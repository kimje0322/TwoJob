package com.blocker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blocker.dto.InvestmentDto;
import com.blocker.repository.InvestmentRepository;

@Service
public class InvestmentService {

	@Autowired
	InvestmentRepository investmentRepository;

	public List<InvestmentDto> getAllMyinvestmentList(String userid, int page) {
		PageRequest pageRequest = PageRequest.of(page, 9);
		return investmentRepository.findAllByUserid(pageRequest, userid).getContent();
	}

	public List<InvestmentDto> getAllInvestmentList(int page) {
		PageRequest pageRequest = PageRequest.of(page, 9);

		return investmentRepository.findAll(pageRequest).getContent();
	}
}
