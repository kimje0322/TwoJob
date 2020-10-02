package com.blocker.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.blocker.dto.ChatMessage;
import com.blocker.dto.Chatroom;
import com.blocker.dto.Member;
import com.blocker.dto.Wallet;
import com.blocker.repository.ChatRoomRepository;
import com.blocker.repository.MemberRepository;
import com.blocker.repository.MessageRepository;
import com.blocker.wrapper.TJToken;

@Service
public class ChatServiceImpl implements ChatService{
	@Autowired
	ChatRoomRepository chatRoomRepository;
	@Autowired
	MessageRepository MessageRepository;
	@Autowired
	LoginService loginService;
	@Autowired
	MemberRepository memberRepository;
	// 모든 방 리스트
	// 방 id로 찾기
	// 방만들기
	// 메시지 보내기

	@Override
	public Object makeChatRoom(String from, String to) {
		Object result =  loginService.getUserInfo(from);
		String user1, user2;
		if(result.getClass() == Member.class) {
			Member m = (Member)result;
			Optional<Member> mem = memberRepository.findById(to);
			if(mem.isPresent()) {
				long val1 = Long.parseLong(m.getOauthId());
				long val2 = Long.parseLong(to);
				if(val1>val2) {
					user1 = to;
					user2 = m.getOauthId();
				}else if(val1 < val2) {
					user1 = m.getOauthId();
					user2 = to;
				}else {
					return "fail";
				}
				Optional<Chatroom> ch1 = chatRoomRepository.findByUser1AndUser2(user1, user2);
				if(ch1.isPresent()) {
					return ch1.get();
				}else {
					Chatroom c = new Chatroom();
					c.setUser1(user1);
					c.setUser2(user2);
					chatRoomRepository.save(c);
					return "success";
				}
			}else {
				return "fail";
			}
		}else if(result.getClass() == String.class){
			return (String)result;
		}else {
			return String.valueOf(result);
		}
	}
	@Override
	public Object getMyChat(String accessToken){ 
		Object result =  loginService.getUserInfo(accessToken);
		if(result.getClass() == Member.class) {
			Member m = (Member)result;
			List<Chatroom> chat = chatRoomRepository.findByUser1OrUser2(m.getOauthId(), m.getOauthId());
			for(int i=0; i<chat.size(); i++) {
				if(chat.get(i).getUser1().equals(m.getOauthId())){
					Optional<Member> m2 = memberRepository.findById(chat.get(i).getUser2());
					chat.get(i).setToimg(m2.get().getProfileImg());
					chat.get(i).setUser1(m.getName());
					chat.get(i).setUser2(m2.get().getName());
				}else {
					Optional<Member> m2 = memberRepository.findById(chat.get(i).getUser1());
					chat.get(i).setToimg(m2.get().getProfileImg());
					chat.get(i).setUser1(m2.get().getName());
					chat.get(i).setUser2(m.getName());
				}
			}
			return chat;
		}else if(result.getClass() == String.class){
			return (String)result;
		}else {
			return String.valueOf(result);
		}
	}

	// 특정 채팅방 조회
	@Override
	public Chatroom getRoom(Long id) {
		Optional<Chatroom> c = chatRoomRepository.findById(id);
		if(c.isPresent()) {
			return c.get();
		}else {
			return null;
		}
	}
	public Page<ChatMessage> message(Long id,PageRequest pageable) {
		return MessageRepository.findByRoomId(id, pageable);
	}
	@Override
	public void saveMessage(ChatMessage msg) {
		MessageRepository.save(msg);
	}
}
