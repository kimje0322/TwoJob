package com.blocker.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.blocker.dto.Member;
import com.blocker.service.ChatService;
import com.blocker.util.PageRequest;

import io.swagger.annotations.ApiOperation;
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
	
	// 룸 이름, 보낸사람, 받는사람, 시간, 이미지 
	@ApiOperation(value ="[채팅방 목록] 내가 참여한 채팅방의 목록을 가져온다. params : [accessToken] result : List<chatRoom>")
	@GetMapping("/chatlist")
	public ResponseEntity<?> myroomlist(@RequestParam("accessToken") String accessToken) {
		Object result = chatService.getMyChat(accessToken);
		if(result.getClass() == List.class || result.getClass() == ArrayList.class){
    		return new ResponseEntity<List<Chatroom>>((List<Chatroom>)result, HttpStatus.OK);
    	}else if(result.getClass() == String.class) {
    		return new ResponseEntity<String>((String)result, HttpStatus.OK);
    	}else {
    		return new ResponseEntity<Integer>((Integer)result, HttpStatus.OK);
    	}
	}
	@ApiOperation(value ="[채팅시작하기] 특정한 사람과의 채팅을 시작(채팅방 생성), 보내는 사람의 accessToken과 받는사람의 OauthId를 활용함  params : [fromAccessToken, toOauthID] result : 성공하면 success, 실패하면 fail")
	@PostMapping("/startchat")
	public ResponseEntity<?> createRoom(@RequestParam("fromAccessToken") String fromAccessToken, @RequestParam("toOauthID") String toId) {
		Object result = chatService.makeChatRoom(fromAccessToken, toId);
		if(result.getClass() == Chatroom.class){
    		return new ResponseEntity<Chatroom>((Chatroom)result, HttpStatus.OK);
    	}else if(result.getClass() == String.class) {
    		return new ResponseEntity<String>((String)result, HttpStatus.OK);
    	}else {
    		return new ResponseEntity<Integer>((Integer)result, HttpStatus.OK);
    	}
	}
	
	@ApiOperation(value ="[특정 채팅방의 정보를 가져옵니다.] roomId에 따라 특정 채팅방의 정보를 가져옵니다. params : [roomId] result : 성공하면 해당 채팅방 return, 없으면 null 리턴")
	@GetMapping("/room/{roomId}")
	public Chatroom roomInfo(@PathVariable Integer roomId) {
		return chatService.getRoom(Long.valueOf(roomId));
	}
	@GetMapping("/getMessage")
	public Page<ChatMessage> getMessage(@RequestParam("roomId") Long id, PageRequest pageable) {
		return chatService.message(id, pageable.of());
	}
}
