package com.blocker.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.dto.EditorSaleDto;
import com.blocker.dto.SaleBoardDto;
import com.blocker.repository.EditorSaleRepository;
import com.blocker.repository.SaleBoardRepository;
import com.blocker.request.SaleBoardRequest;
import com.blocker.util.BasicResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/sale")
public class SaleController {

	@Autowired
	SaleBoardRepository saleBoardRepository;
	@Autowired
	EditorSaleRepository editorSaleRepository;

	@PostMapping("/create")
	@ApiOperation(value = "판매 게시글 생성")
	public Object createSale(@RequestBody SaleBoardRequest saleBoardRequest) {
		final BasicResponse result = new BasicResponse();
		try {
			SaleBoardDto saleBoardDto = new SaleBoardDto(saleBoardRequest);
			SaleBoardDto tempSaleBoardDto = saleBoardRepository.save(saleBoardDto);
			
			// editor에 값넣기
			EditorSaleDto editorSaleDto = new EditorSaleDto();
			editorSaleDto.setEditorhtml(saleBoardRequest.getEditorhtml());
			editorSaleDto.setAddress(saleBoardRequest.getAddress());
			editorSaleRepository.save(editorSaleDto);
			result.data = "success";
			result.status = true;
		} catch (Exception e) {
			result.data = "fail";
			result.status = false;
			System.out.println("판매 게시글 생성중 애러 발생");
			e.printStackTrace();
		}finally {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
}
