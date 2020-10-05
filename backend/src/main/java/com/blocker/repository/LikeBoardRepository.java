package com.blocker.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocker.dto.LikeBoardDto;

@Repository
public interface LikeBoardRepository extends JpaRepository<LikeBoardDto, String> {
	@Query(value = "insert into likeboard (address, userid,ischecked) values (:address,:userid,:ischecked)", nativeQuery = true)
	LikeBoardDto CreateLike(String address, String userid, boolean ischecked);

	@Query(value = "select * from likeboard where address =:address and userid =:userid", nativeQuery = true)
	Optional<LikeBoardDto> findLikeBoardDto(String address, String userid);

	@Modifying
	@Transactional
	@Query(value = "UPDATE likeboard set ischecked = :ischecked, createat = current_time where address =:address and userid =:userid", nativeQuery = true)
	int updateLikeboardDto(String address, String userid, boolean ischecked);

	@Query(value = "select count(*) from likeboard where address =:address and ischecked = true", nativeQuery = true)
	int countLikeBoard(String address);

	@Query(value = "select * from likeboard where userid =:userid and ischecked = true", nativeQuery = true)
	List<LikeBoardDto> getAllProjectILike(String userid);

}
