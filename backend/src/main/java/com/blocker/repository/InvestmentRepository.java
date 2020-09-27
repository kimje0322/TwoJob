package com.blocker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blocker.dto.InvestmentDto;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentDto, String> {
	List<InvestmentDto> findAllByUserid(String userid);
}
