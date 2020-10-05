package com.blocker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.blocker.dto.LikeBoardDto;
import com.blocker.repository.LikeBoardRepository;
import com.blocker.request.CreateLikeRequest;

@Service
public class LikeBoardService {

	@Autowired
	LikeBoardRepository likeBoardRepository;

	public LikeBoardDto CreateLike(LikeBoardDto plikeBoardDto) {
		return likeBoardRepository.save(plikeBoardDto);
	}

	public Optional<LikeBoardDto> findLike(CreateLikeRequest createLikeRequest) {
		return likeBoardRepository.findLikeBoardDto(createLikeRequest.getAddress(), createLikeRequest.getUserid());
	}
	
	public int updateLike(LikeBoardDto plikeBoardDto) {
		return likeBoardRepository.updateLikeboardDto(plikeBoardDto.getAddress(), plikeBoardDto.getUserid(), plikeBoardDto.isIschecked());
	}
	
	public int likeCount(String address) {
		return likeBoardRepository.countLikeBoard(address);
	}
	
	public List<LikeBoardDto> getAllProjectILike(String userid){
		return likeBoardRepository.getAllProjectILike(userid);
	}
	
}
