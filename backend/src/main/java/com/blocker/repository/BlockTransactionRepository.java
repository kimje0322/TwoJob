package com.blocker.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blocker.dto.BlockTransaction;
import com.blocker.dto.TransactType;

public interface BlockTransactionRepository extends JpaRepository<BlockTransaction, Long>{
	List<BlockTransaction> findByFromaddressAndType(String address,TransactType type);
	List<BlockTransaction> findByToaddressAndType(String address,TransactType type);
	Page<BlockTransaction> findByFromaddressAndTypeflag(String address, int typeflag,Pageable pageable);
	List<BlockTransaction> findByFromaddressAndType(String address,TransactType type,Pageable pageable);
	List<BlockTransaction> findDistinctFromAddressByToaddressAndType(String address, TransactType type);
}
