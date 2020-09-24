package com.blocker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blocker.dto.CategoryDto;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDto, String> {

	Optional<CategoryDto> findCategoryDtoByCategoryname(String categoryname);
}
