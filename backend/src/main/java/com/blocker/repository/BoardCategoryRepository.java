package com.blocker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocker.dto.BoardCategoryDto;

@Repository
public interface BoardCategoryRepository extends JpaRepository<BoardCategoryDto, String> {

	@Query(value = "select * from boardcategory where investaddress =:investaddress", nativeQuery = true)
	List<BoardCategoryDto> findAllBoardCategoryDtoByAddress(String investaddress);

}
