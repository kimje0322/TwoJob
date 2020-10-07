package com.blocker.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blocker.dto.BlockTransaction;
import com.blocker.dto.InvestmentDto;
import com.blocker.dto.TransactType;
import com.blocker.dto.Wallet;
import com.blocker.repository.BlockTransactionRepository;
import com.blocker.repository.InvestmentRepository;
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
	@Autowired
	InvestmentRepository investmentRepository;
	@Override
	public Map<String, String> myProject(String id) throws Exception{
		return fundingService.getMyProject(id);
	}
	
	@Override
	public Page<BlockTransaction> tojalist(String id,PageRequest pageable) {
		Optional<Wallet> mywallet = walletRepository.findById(id);
		if(mywallet.isPresent()) {
			String address = mywallet.get().getAddress();
			return blockTransactionRepository.findByFromaddress(address, pageable);
		}else {
			return null;
		}

	}
	@Override
	public Page<BlockTransaction> salelist(String id,PageRequest pageable) {
		Optional<Wallet> mywallet = walletRepository.findById(id);
		if(mywallet.isPresent()) {
			String address = mywallet.get().getAddress();
			return blockTransactionRepository.findByFromaddressAndTypeflag(address,2, pageable);
		}else {
			return null;
		}

	}
	@Override
	public List<MySellPList> getsaleList(String id, PageRequest pageable){
		Optional<Wallet> mywallet = walletRepository.findById(id);
		if(mywallet.isPresent()) {
			String address = mywallet.get().getAddress();
			List<BlockTransaction> list = blockTransactionRepository.findByFromaddressAndType(address, TransactType.SALEPARTIN, pageable);
			List<MySellPList> mysellp = new ArrayList<>();
			for(int i=0; i<list.size(); i++) {
				BlockTransaction bt = list.get(i);
				Optional<InvestmentDto> invest = investmentRepository.findById(bt.getToaddress());
				mysellp.add(new MySellPList(bt.getImage(), bt.getPjtname(), invest.get().getOnelineintro(),String.valueOf(bt.getValue()), bt.getTime()));
			}
			return mysellp;
		}else {
			return null;
		}
	}
}
