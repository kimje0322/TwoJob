package com.blocker.request;

import lombok.Data;

@Data
public class CreateCommentRequest {
	String address;
	String userid;
	String comment;
}
