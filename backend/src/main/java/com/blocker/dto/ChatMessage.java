package com.blocker.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "chatmessage")
public class ChatMessage {
	public enum MessageType {
		ENTER, TALK
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long mid; 
	
	@Column(name = "roomId")
	private Long roomId; 
	
	@Column
	private String sender;
	
	@Column(columnDefinition = "TEXT")
    private String message;
	
	@Column
    private String time;
	
	@Column
	private MessageType type;
	public ChatMessage(Long roomid, String sender, String message, String time) {
		super();
		this.roomId = roomid;
		this.sender = sender;
		this.message = message;
		this.time = time;
	}
}
