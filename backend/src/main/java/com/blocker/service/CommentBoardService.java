package com.blocker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blocker.repository.CommentBoardRepository;

@Service
public class CommentBoardService {

	@Autowired
	CommentBoardRepository commentBoardRepository;
}
