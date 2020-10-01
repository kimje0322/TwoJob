package com.blocker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blocker.dto.LikeBoardDto;

@Repository
public interface LikeBoardRepository extends JpaRepository<LikeBoardDto, String> {

}
