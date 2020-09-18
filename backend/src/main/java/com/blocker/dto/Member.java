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
public class Member {

    @Id
    @Column
    private String oauthId;

    @Column
    private String name;
    
    @Column
    private String profileImg;
    
    @Column
    private String providerName;
    
    @Column
    private String accessToken;
	public Member(String oauthId, String name, String profileImg, String providerName, String accessToken) {
		super();
		this.oauthId = oauthId;
		this.name = name;
		this.profileImg = profileImg;
		this.providerName = providerName;
		this.accessToken = accessToken;
	}

}