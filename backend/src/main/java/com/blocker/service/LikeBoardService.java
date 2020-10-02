package com.blocker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blocker.repository.LikeBoardRepository;

@Service
public class LikeBoardService {

	@Autowired
	LikeBoardRepository likeBoardRepository;
}
