package com.blocker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocker.dto.CommentBoardDto;

@Repository
public interface CommentBoardRepository extends JpaRepository<CommentBoardDto, String> {

	@Query(value = "select * from commentboard where address = :address",nativeQuery = true)
	List<CommentBoardDto> findAllCommentsByAddress(String address);
}
