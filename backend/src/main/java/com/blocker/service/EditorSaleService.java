package com.blocker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blocker.dto.EditorSaleDto;
import com.blocker.repository.EditorSaleRepository;
import com.blocker.repository.SaleBoardRepository;

import okhttp3.internal.cache.DiskLruCache.Editor;

@Service
public class EditorSaleService {

	@Autowired
	EditorSaleRepository editorSaleRepository;

	public EditorSaleDto getEditorSaleDto(String address) {
		return editorSaleRepository.findEditorSaleDtoByAddress(address);
	}

}
