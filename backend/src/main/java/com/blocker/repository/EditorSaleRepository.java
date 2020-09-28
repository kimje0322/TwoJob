package com.blocker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blocker.dto.EditorSaleDto;

@Repository
public interface EditorSaleRepository extends JpaRepository<EditorSaleDto, String> {
	EditorSaleDto findEditorSaleDtoByAddress(String address);
}
