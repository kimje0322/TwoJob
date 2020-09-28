package com.blocker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blocker.dto.InvestmentDto;
import com.blocker.dto.SaleBoardDto;
import com.blocker.repository.SaleBoardRepository;

@Service
public class SaleService {
	@Autowired
	SaleBoardRepository saleBoardRepository;

	public List<SaleBoardDto> getAllMySaleList(String userid, int page) {
		PageRequest pageRequest = PageRequest.of(page, 9);
		return saleBoardRepository.findAllByUserid(pageRequest, userid);
	}
	
	public List<SaleBoardDto> getAllSaleList(int page) {
		PageRequest pageRequest = PageRequest.of(page, 9);

		return saleBoardRepository.findAll(pageRequest).getContent();
	}
}
