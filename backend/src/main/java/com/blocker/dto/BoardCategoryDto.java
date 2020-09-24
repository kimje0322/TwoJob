package com.blocker.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "boardcategory")
@Data
public class BoardCategoryDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int num;
	@Column
	String investaddress;
	@Column
	String categoryname;
}
