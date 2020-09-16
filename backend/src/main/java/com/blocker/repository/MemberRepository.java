package com.blocker.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blocker.dto.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	public Member findByOauthId(String id); 
}