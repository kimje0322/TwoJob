package com.blocker.service;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.blocker.repository.CommentBoardRepository;

@Service
public class CommentBoardService {

	@Autowired
	CommentBoardRepository commentBoardRepository;
}
