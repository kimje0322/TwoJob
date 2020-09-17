package com.blocker.dto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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