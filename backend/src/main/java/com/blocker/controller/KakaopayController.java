package com.blocker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.util.KakaoPay;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/kakaopay")
public class KakaopayController {

	@Autowired
	private KakaoPay kakaopay;

	@GetMapping("/kakaoPay")
	@ApiOperation(value = "카카오페이 get방식")
	public void kakaoPayGet() {

	}

	@ResponseBody
	@PostMapping("/kakaoPay")
	@ApiOperation(value = "카카오페이 Post방식")
	// 프론트에서 상품명 수량 총액 가져와서 kakaoPayReady의 파라메타로 넘겨서
	// kakaoPayReady의 필수데이터를 초기화 해주어야함
	public String kakaoPay(@RequestParam int count) {
		return kakaopay.kakaoPayReady(count);
	}

	@ResponseBody
	@GetMapping("/kakaoPayReadySuccess")
	@ApiOperation(value = "카카오페이 성공했을 경우")
	public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, String totalprice, Model model) {
		System.out.println("kakaoPaySuccess get----------------------");
		System.out.println("kakaoPaySuccess pg_token : " + pg_token);

		model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token, totalprice));
		return "http://localhost:8080/";
	}
}
