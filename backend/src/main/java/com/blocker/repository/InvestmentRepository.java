package com.blocker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocker.dto.InvestmentDto;
import java.lang.String;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentDto, String> {
	@Query(value = "select * from investmentboard where userid = :userid", countQuery = "select count(*) from investmentboard where userid = :userid", nativeQuery = true)
	Page<InvestmentDto> findAllByUserid(Pageable page, String userid);

//	@Query(value = "select * from investmentboard where userid = :userid", nativeQuery = true)
//	List<InvestmentDto> findAllByuseridWithoutPageNav(String userid);

	Optional<InvestmentDto> findInvestmentDtoByAddress(String address);
	@Query(value = "select * from investmentboard order by createat desc", countQuery = "select count(*) from investmentboard order by createat", nativeQuery = true)
	Page<InvestmentDto> findAllInvestmentDtoOrderbyCreatedat(Pageable page);

	@Query(value = "select * from investmentboard i order by (select count(*) from likeboard where address = i.address) desc", nativeQuery = true)
	Page<InvestmentDto> findAllInvestmentDtoOrderbyLikecount(Pageable page);

	@Query(value = "select * from investmentboard where isfinish != true order by deadline limit 3", nativeQuery = true)
	List<InvestmentDto> findThreeInvestmentboardOrderbyDeadline();

	@Query(value = "select * from investmentboard i order by (select count(*) from likeboard where address = i.address) desc limit 3", nativeQuery = true)
	List<InvestmentDto> findThreeInvestmentboardOrderbyLikecount();

	List<InvestmentDto> findByIsfinishOrderByDeadlineAsc(Boolean flag);

}
