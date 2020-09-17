package com.blocker.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Userinfo {

	@Id
    @Column
    private String email;

    @Column
    private String nickname;
    
    @Column
    private String image;
    
    @Column
    private String password;

	public Userinfo(String email, String nickname, String image, String password) {
		super();
		this.email = email;
		this.nickname = nickname;
		this.image = image;
		this.password = password;
	}
    
}
