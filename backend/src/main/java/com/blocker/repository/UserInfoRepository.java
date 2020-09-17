package com.blocker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocker.dto.Userinfo;

public interface UserInfoRepository extends JpaRepository<Userinfo, String>{
	public Optional<Userinfo> findByEmail(String email);
}
