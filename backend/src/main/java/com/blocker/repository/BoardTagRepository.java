package com.blocker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocker.dto.BoardTagDto;
import com.blocker.dto.TagDto;

@Repository
public interface BoardTagRepository extends JpaRepository<BoardTagDto, String> {

	@Query(value = "select * from boardtag where investaddress = :investaddress", nativeQuery = true)
	List<BoardTagDto> findAllBoardTagDtoByInvestaddress(String investaddress);
}
