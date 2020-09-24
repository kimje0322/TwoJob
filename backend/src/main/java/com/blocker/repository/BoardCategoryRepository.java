package com.blocker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blocker.dto.BoardCategoryDto;

@Repository
public interface BoardCategoryRepository extends JpaRepository<BoardCategoryDto, String>{

}