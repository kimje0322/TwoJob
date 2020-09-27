package com.blocker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blocker.dto.EditorInvestmentDto;

@Repository
public interface EditorInvestmentRepository extends JpaRepository<EditorInvestmentDto, String>{
	Optional<EditorInvestmentDto> getEditorInvestmentDtoByInvestaddress(String investaddress);
}
