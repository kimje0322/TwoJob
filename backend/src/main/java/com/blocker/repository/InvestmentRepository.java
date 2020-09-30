package com.blocker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocker.dto.InvestmentDto;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentDto, String> {
	@Query(value = "select * from investmentboard where userid = :userid", countQuery = "select count(*) from investmentboard where userid = :userid", nativeQuery = true)
	Page<InvestmentDto> findAllByUserid(Pageable page, String userid);

//	@Query(value = "select * from investmentboard where userid = :userid", nativeQuery = true)
//	List<InvestmentDto> findAllByuseridWithoutPageNav(String userid);

	Optional<InvestmentDto> findInvestmentDtoByAddress(String address);
}
