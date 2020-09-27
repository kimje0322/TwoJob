package com.blocker.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.dto.EditorSaleDto;
import com.blocker.dto.SaleBoardDto;
import com.blocker.repository.EditorSaleRepository;
import com.blocker.repository.InvestmentRepository;
import com.blocker.repository.SaleBoardRepository;
import com.blocker.request.SaleBoardRequest;
import com.blocker.util.BasicResponse;
import com.blocker.util.webhook;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/sale")
public class SaleController {

	@Autowired
	SaleBoardRepository saleBoardRepository;
	@Autowired
	EditorSaleRepository editorSaleRepository;
	@Autowired
	InvestmentRepository investmentRepository;

	@PostMapping("/create")
	@ApiOperation(value = "판매 게시글 생성")
	public Object createSale(@RequestBody SaleBoardRequest saleBoardRequest) {
		final BasicResponse result = new BasicResponse();
		try {
			SaleBoardDto saleBoardDto = new SaleBoardDto(saleBoardRequest);
			saleBoardDto.setAddress(UUID.randomUUID().toString());
			SaleBoardDto tempSaleBoardDto = saleBoardRepository.save(saleBoardDto);

			// editor에 값넣기
			EditorSaleDto editorSaleDto = new EditorSaleDto();
			editorSaleDto.setEditorhtml(saleBoardRequest.getEditorhtml());
			editorSaleDto.setAddress(saleBoardDto.getAddress());
			editorSaleRepository.save(editorSaleDto);
			result.data = "success";
			result.status = true;
		} catch (Exception e) {
			result.data = "fail";
			result.status = false;
			System.out.println("판매 게시글 생성중 애러 발생");
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@GetMapping("/saleList")
	@ApiOperation(value = "판매 게시글 리스트 가져오기")
	public Object saleList(@RequestParam String userid) {
		final BasicResponse result = new BasicResponse();
		List<SaleBoardRequest> resultDatalist = new ArrayList<SaleBoardRequest>();
		List<SaleBoardDto> list = saleBoardRepository.findAllByUserid(userid);

		try {
			for (Iterator<SaleBoardDto> iter = list.iterator(); iter.hasNext();) {
				SaleBoardDto saleBoardDto = iter.next();
				EditorSaleDto editorSaleDto = editorSaleRepository
						.findEditorSaleDtoByAddress(saleBoardDto.getAddress());
				SaleBoardRequest data = new SaleBoardRequest();
				if (!editorSaleDto.getEditorhtml().isEmpty()) {
					data.setEditorhtml(editorSaleDto.getEditorhtml());
				}
				data.setInvestaddress(saleBoardDto.getInvestaddress());
				data.setPicture(saleBoardDto.getPicture());
				data.setPjtname(saleBoardDto.getPjtname());
				data.setSaleprice(saleBoardDto.getSaleprice());
				data.setStartdate(saleBoardDto.getStartdate());
				data.setUrl(saleBoardDto.getUrl());
				data.setUserid(saleBoardDto.getUserid());
				resultDatalist.add(data);
			}
			result.data = "success";
			result.object = resultDatalist;
			result.status = true;
			result.status = true;
		} catch (Exception e) {
			result.data = "fail";
			result.status = false;
			System.out.println("판매 게시글 리스트 추출중 애러 발생");
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
	@ExceptionHandler(Exception.class)
	public void nullex(Exception e) {
		System.err.println("sale 부분에서 " + e.getClass());
		webhook w = new webhook();
		w.send("sale 부분에서 " + e.getClass());
	}
}
