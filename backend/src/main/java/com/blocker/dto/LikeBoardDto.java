package com.blocker.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "likeboard")
public class LikeBoardDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int num;
	@Column
	String address;
	@Column
	String userid;
	@Column
	boolean ischecked;
	@Column(name = "createat", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	String createat;
}
