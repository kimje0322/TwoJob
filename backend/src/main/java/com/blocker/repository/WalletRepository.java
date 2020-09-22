package com.blocker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocker.dto.Wallet;


public interface WalletRepository extends JpaRepository<Wallet, String>{
	public Optional<Wallet> findByAddress(String address);
}
