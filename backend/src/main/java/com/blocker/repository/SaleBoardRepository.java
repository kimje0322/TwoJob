package com.blocker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocker.dto.InvestmentDto;
import com.blocker.dto.SaleBoardDto;

@Repository
public interface SaleBoardRepository extends JpaRepository<SaleBoardDto, String> {

	@Query(value = "select * from saleboard where userid = :userid", countQuery = "selet count (*) from saleboard where userid =: userid", nativeQuery = true)
	Page<SaleBoardDto> findAllByUserid(Pageable page, String userid);

	Optional<SaleBoardDto> findSaleBoardDtoByAddress(String address);

	@Query(value = "select * from saleboard where investaddress =:investaddress", nativeQuery = true)
	Optional<SaleBoardDto> findSaleBoardDtoByInvestaddress(String investaddress);

	@Query(value = "select * from saleboard order by createat desc", countQuery = "select count(*) from saleboard order by createat", nativeQuery = true)
	Page<SaleBoardDto> findAllSaleBoardDtoOrderbyCreatedat(Pageable page);

	@Query(value = "select * from saleboard i order by (select count(*) from likeboard where address = i.address) desc", countQuery = "select count(*) from saleboard i order by (select count(*) from likeboard where address = i.address) desc", nativeQuery = true)
	Page<SaleBoardDto> findAllSaleBoardDtoOrderbyLikecount(Pageable page);

	@Query(value = "select * from saleboard order by startdate limit 3", nativeQuery = true)
	List<SaleBoardDto> findThreeSaleboardOrderbyStartdate();

	@Query(value = "select * from saleboard i order by (select count(*) from likeboard where address = i.address) desc limit 4", nativeQuery = true)
	List<SaleBoardDto> findThreeSaleboardOrderbyLikecount();

}
