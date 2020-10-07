package com.blocker.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "blocktransact")
public class BlockTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String blockhash;

	@Column
	private String toaddress;

	@Column
	private String pjtname;
	@Column
	private String fromaddress;

	@Column
	private String image;

	@Column(name = "val")
	private Double value;

	@Column
	@Enumerated(EnumType.STRING)
	private TransactType type;
	@CreationTimestamp
	private Date time;	
	@Column
	private int typeflag;
	public BlockTransaction(String blockhash, String toaddress, String compName, String fromaddress, String image,
			Double value, TransactType type, int typeflag) {
		super();
		this.blockhash = blockhash;
		this.toaddress = toaddress;
		this.pjtname = compName;
		this.fromaddress = fromaddress;
		this.image = image;
		this.value = value;
		this.type = type;
		this.typeflag = typeflag;
	}



}
