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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "wallets")
public class Wallet {
	@Id
	@Column(name = "user_id")
    private String oauthId;
	
	@Column
    private String address;
    
    @Column
    private int balance;
    
    @Column
    private String privatekey;
    
	public Wallet(String oauthId, String address, int balance, String privatekey) {
		super();
		this.oauthId = oauthId;
		this.address = address;
		this.balance = balance;
		this.privatekey = privatekey;
	}
    
}
