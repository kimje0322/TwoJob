package com.blocker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.dto.Member;
import com.blocker.request.LoginResponse;
import com.blocker.service.LoginService;
import com.blocker.util.webhook;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/login")
public class LoginContoroller {
	@Autowired
	LoginService loginService;

	@PostMapping("/kakaologin")
	@ApiOperation(value = "[카카오 로그인] accessToken을 전달해 로그인한다, 만약 사용자 정보가 제대로 넘어오면, Member Dto, 잘못된 access면 responseCode return, 실패하면 fail을 return")
	public ResponseEntity<?> login(@RequestParam("accessToken") String accessToken) {
		Object result = loginService.getUserInfo(accessToken);
		if (result.getClass() == Member.class) {

			return new ResponseEntity<Member>((Member) result, HttpStatus.OK);
		} else if (result.getClass() == String.class) {
			return new ResponseEntity<String>((String) result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>((Integer) result, HttpStatus.OK);
		}

	}

	@GetMapping("/kakaologout")
	@ApiOperation(value = "[카카오 로그아웃] accessToken을 전달해 해당 사용자 로그인한다, 만약 로그아웃 성공이면 success, 잘못된 access면 responseCode return, 실패하면 fail을 return")
	public ResponseEntity<String> logout(@RequestParam("accessToken") String accessToken) {
		return new ResponseEntity<String>(loginService.Logout(accessToken), HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	public void nullex(Exception e) {
		System.err.println("login 부분에서 " + e.getClass());
		webhook w = new webhook();
		w.send("login 부분에서 " + e.getClass());
	}
}
