package com.blocker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blocker.dto.InvestmentDto;
import com.blocker.dto.SaleBoardDto;
import com.blocker.repository.SaleBoardRepository;

@Service
public class SaleService {
	@Autowired
	SaleBoardRepository saleBoardRepository;

	public Page<SaleBoardDto> getAllMySaleList(String userid, int page) {
		PageRequest pageRequest = PageRequest.of(page, 9);
		return saleBoardRepository.findAllByUserid(pageRequest, userid);
	}

	public Page<SaleBoardDto> getAllSaleList(int page) {
		PageRequest pageRequest = PageRequest.of(page, 9);

		return saleBoardRepository.findAll(pageRequest);
	}
	
	public Page<SaleBoardDto> getAllSaleList(int page, int orderOption){
		PageRequest pageRequest = PageRequest.of(page, 9);
		
		//최신순
		if(orderOption ==1) {
			return saleBoardRepository.findAllSaleBoardDtoOrderbyCreatedat(pageRequest);
		}else { //인기순
			return saleBoardRepository.findAllSaleBoardDtoOrderbyLikecount(pageRequest);
		}
	}

	public Optional<SaleBoardDto> getSaleBoard(String address) {
		return saleBoardRepository.findSaleBoardDtoByAddress(address);
	}

	public Optional<SaleBoardDto> getSaleBoardByInvestAddress(String investaddress) {
		return saleBoardRepository.findSaleBoardDtoByInvestaddress(investaddress);
	}
	
	public List<SaleBoardDto> getThreeSaleListOrderbyStartdate(){
		return saleBoardRepository.findThreeSaleboardOrderbyStartdate();
	}
	
	public List<SaleBoardDto> getThreeSaleListOrderbyLikecount(){
		return saleBoardRepository.findThreeSaleboardOrderbyLikecount();
	}
}
