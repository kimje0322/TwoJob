package com.blocker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blocker.dto.CommentBoardDto;
import com.blocker.repository.CommentBoardRepository;

@Service
public class CommentBoardService {

	@Autowired
	CommentBoardRepository commentBoardRepository;

	public CommentBoardDto createComment(CommentBoardDto commentBoardDto) {
		return commentBoardRepository.save(commentBoardDto);
	}

	public List<CommentBoardDto> getAllComment(String address) {
		return commentBoardRepository.findAllCommentsByAddress(address);
	}
	
	public CommentBoardDto getcomment(String address, String userid) {
		return commentBoardRepository.getCommentByAddressAndUserid(address, userid);
	}
	
	public int deleteComment(int num) {
		return commentBoardRepository.deleteCommentByNum(num);
	}
}
