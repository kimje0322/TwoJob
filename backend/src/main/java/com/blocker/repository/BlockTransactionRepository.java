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
	Page<BlockTransaction> findByFromaddress(String address,Pageable pageable);
	@Query(value = "SELECT DISTINCT(p.fromaddress) FROM blocktransact p WHERE p.toaddress = ?1 and p.type = ?2", nativeQuery = true)
	List<String> findthis(String address, String type);
}
