package com.blocker.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Chatroom {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomid; 
	
	@Column
	private String user1;
	
	@Column
	private String user2;
	
	private String toimg;
	
	public Chatroom(String user1, String user2,String toimg) {
		super();
		this.user1 = user1;
		this.user2 = user2;
		this.toimg = toimg;
	}
	
}
