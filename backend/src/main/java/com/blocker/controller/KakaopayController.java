package com.blocker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.service.TokenService;
import com.blocker.util.KakaoPay;
import com.blocker.util.webhook;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/kakaopay")
public class KakaopayController {

	@Autowired
	private KakaoPay kakaopay;

	@Autowired
	private TokenService tokenService;
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
	public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, String totalprice, String access_token) throws Exception {
		System.out.println("kakaoPaySuccess get----------------------");
		System.out.println("kakaoPaySuccess pg_token : " + pg_token);

		kakaopay.kakaoPayInfo(pg_token, totalprice);
		Integer money = Integer.parseInt(totalprice);
		//토큰 충전 
		tokenService.TransferTo(access_token, money);
	}
	@ExceptionHandler(Exception.class)
	public void nullex(Exception e) {
		System.err.println("kakaopay부분 " + e.getClass());
		webhook w = new webhook();
		w.send("kakaopay 부분에서 " + e.getClass());
	}
}
