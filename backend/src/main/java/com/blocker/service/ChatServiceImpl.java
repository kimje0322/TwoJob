package com.blocker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blocker.dto.ChatMessage;
import com.blocker.dto.Chatroom;
import com.blocker.repository.ChatRoomRepository;
import com.blocker.repository.MessageRepository;

@Service
public class ChatServiceImpl implements ChatService{
	@Autowired
	ChatRoomRepository chatRoomRepository;
	@Autowired
	MessageRepository MessageRepository;
	// 모든 방 리스트
	// 방 id로 찾기
	// 방만들기
	// 메시지 보내기
	
	@Override
	public void makeChatRoom(String from, String to) {
		Chatroom room = new Chatroom();
		room.setUser1(from);
		room.setUser2(to);
		chatRoomRepository.save(room);
	}
	
	// 사용자 이름으로 바꿔야함.
	@Override
	public List<Chatroom> allroom() {
		return chatRoomRepository.findAll();
	}
	
	// 특정 채팅방 조회
	@Override
	public Chatroom getRoom(Long id) {
		List<ChatMessage> allmesssage = MessageRepository.findByRoomId(id);
		return chatRoomRepository.findById(id).get();
	}
	
	@Override
	public void saveMessage(ChatMessage msg) {
		MessageRepository.save(msg);
	}
}
