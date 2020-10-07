package com.blocker.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocker.dto.BoardCategoryDto;

@Repository
public interface BoardCategoryRepository extends JpaRepository<BoardCategoryDto, String> {

	@Query(value = "select * from boardcategory where investaddress =:investaddress", nativeQuery = true)
	List<BoardCategoryDto> findAllBoardCategoryDtoByAddress(String investaddress);

	@Query(value = "select investaddress from boardcategory where categoryname in (:categorys) group by investaddress having count(investaddress) >= :size", countQuery = "select count(investaddress) from boardcategory where categoryname in (:categorys) group by investaddress having count(investaddress) >= :size", nativeQuery = true)
	Page<BoardCategoryMapping> findAllInvestmentDtoWithCategory(List<String> categorys, int size, Pageable page);

	@Query(value = "select investaddress from boardcategory b where categoryname in (:categorys) group by investaddress having count(investaddress) >= :size order by (select createat from investmentboard where address = b.investaddress) desc", countQuery = "select count(investaddress) from boardcategory b where categoryname in (:categorys) group by investaddress having count(investaddress) >= :size  order by (select createat from investmentboard where address = b.investaddress)", nativeQuery = true)
	Page<BoardCategoryMapping> findAllInvestmentDtoWithCategoryOrderbycreateat(List<String> categorys, int size,
			Pageable page);

	@Query(value = "select investaddress from boardcategory b where categoryname in (:categorys) group by investaddress having count(investaddress) >= :size order by (select count(address) from likeboard where address = b.investaddress) desc", countQuery = "select count(investaddress) from boardcategory b where categoryname in (:categorys) group by investaddress having count(investaddress) >= :size order by (select count(address) from likeboard where address = b.investaddress)", nativeQuery = true)
	Page<BoardCategoryMapping> findAllInvestmentDtoWithCategoryOrderbyLikeCount(List<String> categorys, int size,
			Pageable page);
}
