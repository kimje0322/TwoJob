package com.blocker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocker.dto.User;

public interface AccountRepository extends JpaRepository<User, String>{
	public Optional<User> findUserByUserEmailAndUserPwd(String eamil, String password);
	public Optional<User> findUserByUserEmail(String email);
	public User getUserByUserEmail(String email);
	public Optional<User> getUserByUserNickname(String nickname);
}
