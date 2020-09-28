package com.blocker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocker.dto.ChatMessage;


public interface MessageRepository extends JpaRepository<ChatMessage, Long>{
	public List<ChatMessage> findByRoomId(Long id);
}
