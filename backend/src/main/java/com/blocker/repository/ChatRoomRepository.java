package com.blocker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocker.dto.Chatroom;


public interface ChatRoomRepository extends JpaRepository<Chatroom, Long>{
	List<Chatroom> findByUser1OrUser2(String user1, String user2);
	Optional<Chatroom> findByUser1AndUser2(String user1, String user2);
}
