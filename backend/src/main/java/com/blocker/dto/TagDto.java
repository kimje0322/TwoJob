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
@Table(name ="tags")
public class TagDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int num;
	@Column
	String tagname;
	
	public TagDto() {
		
	}
	
	public TagDto(TagDto tagdto) {
		this.num = tagdto.getNum();
		this.tagname = tagdto.getTagname();
	}
}
