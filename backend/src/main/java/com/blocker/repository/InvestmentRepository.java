package com.blocker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blocker.dto.InvestmentDto;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentDto, String> {

}
