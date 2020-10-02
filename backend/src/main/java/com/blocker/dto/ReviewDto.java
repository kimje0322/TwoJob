package com.blocker.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "review")
@Data
public class ReviewDto {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int num;
	@Column
	String saleaddress;
	@Column
	int satisfied;
	@Column
	int similar;
	@Column
	String picture;
	@Column
	String reviewexplain;
	@Column
	String userid;
	@Column(name = "createdate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	String createdate;
}
