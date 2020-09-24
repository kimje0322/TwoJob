package com.blocker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blocker.dto.SaleBoardDto;

@Repository
public interface SaleBoardRepository extends JpaRepository<SaleBoardDto, String>{

	Optional<SaleBoardDto> findSaleBoardDtoByAddress(String address);
}
