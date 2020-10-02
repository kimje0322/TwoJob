package com.blocker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocker.dto.TagDto;

@Repository
public interface TagRepository extends JpaRepository<TagDto, String> {
	Optional<TagDto> findTagDtoByTagname(String tagname);
	
}
