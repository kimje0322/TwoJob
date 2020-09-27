package com.blocker.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "editorsale")
public class EditorSaleDto {
	@Id
	String address ;
	@Column
	String editorhtml;
	
	public EditorSaleDto() {}
	
	public EditorSaleDto(EditorSaleDto editorSaleDto) {
		this.address = editorSaleDto.getAddress();
		this.editorhtml = editorSaleDto.getEditorhtml();
	}
}
