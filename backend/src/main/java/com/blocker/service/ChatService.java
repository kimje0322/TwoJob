package com.blocker.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.blocker.dto.ChatMessage;
import com.blocker.dto.Chatroom;

public interface ChatService {
	public Object makeChatRoom(String from, String to);
	public Chatroom getRoom(Long id);
	public void saveMessage(ChatMessage msg);
	public Object getMyChat(String accessToken);
	public Page<ChatMessage> message(Long id,PageRequest pageable);
}
