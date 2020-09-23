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
@Table(name="editorinvestment")
public class EditorInvestmentDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int num;
	@Column
	int investnum;
	@Column
	String editorhtml;
}
