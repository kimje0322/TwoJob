package com.blocker.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.dto.ChatMessage;
import com.blocker.dto.Chatroom;
import com.blocker.service.ChatService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
	@Autowired
	ChatService chatService;

	private final SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/chat/message")
	public void message(ChatMessage message) {
		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		message.setTime(simple.format(date));
		if (ChatMessage.MessageType.ENTER.equals(message.getType()))
			message.setMessage(message.getSender() + "님이 입장하셨습니다.");

		if(ChatMessage.MessageType.TALK.equals(message.getType())) {
			chatService.saveMessage(message);
		}
		messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}
	
	@MessageMapping("/notice")
	public void notice(String val) {
		System.out.println("val = " + val);
		messagingTemplate.convertAndSend("/sub/notice","hello");
	}
	@PostMapping("/room")
	public void createRoom(@RequestParam("from") String from, @RequestParam("to") String to) {
		chatService.makeChatRoom(from, to);
	}
	@GetMapping("/allroom")
	public List<Chatroom> allroom() {
		return chatService.allroom();
	}

	@GetMapping("/room/{roomId}")
	public Chatroom roomInfo(@PathVariable Integer roomId) {
		return chatService.getRoom(Long.valueOf(roomId));
	}
	
	
	@GetMapping("/test")
	public void test() {
		
	}
}
