package com.blocker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blocker.dto.BoardTagDto;

@Repository
public interface BoardTagRepository extends JpaRepository<BoardTagDto, String>{

}
