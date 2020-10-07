package com.blocker.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member {

	@Id
	@Column(name = "user_id")
	private String oauthId;

	@Column
	private String name;

	@Column
	private String profileImg;

	@Column
	private String providerName;

	@Column
	private String email;

	@Column
	private String accessToken;

	@Column
    private boolean isfirsttime;

	public Member(String oauthId, String name, String profileImg, String providerName, String email,
			String accessToken) {
		super();
		this.oauthId = oauthId;
		this.name = name;
		this.profileImg = profileImg;
		this.providerName = providerName;
		this.email = email;
		this.accessToken = accessToken;
	}

}