package com.blocker.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.blocker.dto.BlockTransaction;

public interface MyPageService {
	public Map<String, String> myProject(String id) throws Exception;
	public Page<BlockTransaction> tojalist(String id,PageRequest pageable);
	public Page<BlockTransaction> salelist(String id,PageRequest pageable);
	public List<MySellPList> getsaleList(String id, PageRequest pageable);
}
