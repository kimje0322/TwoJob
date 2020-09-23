package com.blocker.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blocker.dto.InvestmentDto;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentDto, String> {

}
