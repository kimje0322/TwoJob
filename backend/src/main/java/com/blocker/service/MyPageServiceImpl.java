package com.blocker.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blocker.dto.BlockTransaction;
import com.blocker.dto.Member;
import com.blocker.dto.Wallet;
import com.blocker.repository.BlockTransactionRepository;
import com.blocker.repository.MemberRepository;
import com.blocker.repository.WalletRepository;


@Service
public class MyPageServiceImpl implements MyPageService{

	@Autowired
	FundingService fundingService;
	@Autowired
	BlockTransactionRepository blockTransactionRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	WalletRepository walletRepository;
	@Override
	public Map<String, String> myProject(String id) throws Exception{
		return fundingService.getMyProject(id);
	}
	
	@Override
	public Page<BlockTransaction> tojalist(String id,PageRequest pageable) {
		Optional<Wallet> mywallet = walletRepository.findById(id);
		if(mywallet.isPresent()) {
			String address = mywallet.get().getAddress();
			return blockTransactionRepository.findByToaddressAndTypeflag(address, 1, pageable);
		}else {
			return null;
		}

	}
	@Override
	public Page<BlockTransaction> salelist(String id,PageRequest pageable) {
		Optional<Wallet> mywallet = walletRepository.findById(id);
		if(mywallet.isPresent()) {
			String address = mywallet.get().getAddress();
			return blockTransactionRepository.findByToaddressAndTypeflag(address,2, pageable);
		}else {
			return null;
		}

	}
}
