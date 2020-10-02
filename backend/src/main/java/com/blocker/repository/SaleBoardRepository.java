package com.blocker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocker.dto.SaleBoardDto;

@Repository
public interface SaleBoardRepository extends JpaRepository<SaleBoardDto, String> {

	@Query(value = "select * from saleboard where userid = :userid", countQuery = "selet count (*) from saleboard where userid =: userid", nativeQuery = true)
	Page<SaleBoardDto> findAllByUserid(Pageable page, String userid);

	Optional<SaleBoardDto> findSaleBoardDtoByAddress(String address);

	@Query(value = "select * from saleboard where investaddress =:investaddress", nativeQuery = true)
	Optional<SaleBoardDto> findSaleBoardDtoByInvestaddress(String investaddress);
}
