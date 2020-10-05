package com.blocker.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocker.dto.CommentBoardDto;

@Repository
public interface CommentBoardRepository extends JpaRepository<CommentBoardDto, String> {

	@Query(value = "select * from commentboard where address = :address", nativeQuery = true)
	List<CommentBoardDto> findAllCommentsByAddress(String address);

	@Query(value = "select * from commentboard where address =:address and userid =:userid", nativeQuery = true)
	CommentBoardDto getCommentByAddressAndUserid(String address, String userid);

	@Transactional
	@Modifying
	@Query(value = "delete from commentboard where num = :num", nativeQuery = true)
	int deleteCommentByNum(int num);
}
