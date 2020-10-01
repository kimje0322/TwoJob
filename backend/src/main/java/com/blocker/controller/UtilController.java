package com.blocker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.service.CommentBoardService;
import com.blocker.service.LikeBoardService;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/util")
public class UtilController {

	@Autowired
	LikeBoardService likeBoardService;
	@Autowired
	CommentBoardService commentBoardService;
}
