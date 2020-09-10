package com.blocker.repository;

import org.springframework.data.repository.CrudRepository;

import com.blocker.dto.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findByUsername(String username);	// user 검색
    Account findByEmail(String email);			// email 검색
    Long deleteByUsername(String username);		// user 삭제 (admin 사용자만)
}

