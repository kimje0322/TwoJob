package com.blocker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blocker.repository.WalletRepository;

@Service
public class WalletServiceImpl {
	
	@Autowired
	WalletRepository walletRepository;
}
