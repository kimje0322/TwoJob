package com.blocker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocker.dto.Chatroom;

public interface ChatRoomRepository extends JpaRepository<Chatroom, Long>{
	
}
