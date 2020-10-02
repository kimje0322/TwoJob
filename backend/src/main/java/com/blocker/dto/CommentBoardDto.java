package com.blocker.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.blocker.request.CreateCommentRequest;

import lombok.Data;

@Data
@Entity
@Table(name = "commentboard")
public class CommentBoardDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int num;
	@Column
	String address;
	@Column
	String userid;
	@Column
	String content;
	@Column(name = "createat", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	String createat;

	public CommentBoardDto() {
	}

	public CommentBoardDto(CreateCommentRequest pcreateCommentRequest) {
		this.address = pcreateCommentRequest.getAddress();
		this.userid = pcreateCommentRequest.getUserid();
		this.content = pcreateCommentRequest.getComment();
	}

}
