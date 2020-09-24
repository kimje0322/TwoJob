package com.blocker.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "boardtag")
public class BoardTagDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int num;
	@Column
	String tagname;
	//만약 여기서 애러뜨면 유니크키 지정안해서 그런거임
	@Column
	String investaddress;
}
