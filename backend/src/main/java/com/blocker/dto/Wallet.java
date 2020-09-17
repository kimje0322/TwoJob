package com.blocker.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wallet {
	@Id
    @Column
    private String oauthId;
	
	@Column
    private String address;
    
    @Column
    private int balance;

	public Wallet(String oauthId, String address, int balance) {
		super();
		this.oauthId = oauthId;
		this.address = address;
		this.balance = balance;
	}
    
}
