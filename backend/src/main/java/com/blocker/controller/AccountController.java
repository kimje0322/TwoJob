package com.blocker.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;


import com.blocker.dto.User;
import com.blocker.repository.AccountRepository;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = { "*" })
@RestController
public class AccountController {

   @Autowired
   AccountRepository accountRepository;

   @GetMapping("/account/login")
   @ApiOperation(value = "로그인")
   public Object login(@RequestParam(required = true) final String email,
         @RequestParam(required = true) final String password) {

      Optional<User> userOpt = accountRepository.findUserByUserEmailAndUserPwd(email, password);
      System.out.println(userOpt+"?");
      ResponseEntity response = null;
      if (userOpt.isPresent()) {
         Map<String,Object> result = new HashMap<>();
         String token = getToken(email);
         result.put("status", true);
         result.put("data",token);
         result.put("object", userOpt);
//            String token = getToken(userOpt);
         System.out.println(userOpt);
         response = new ResponseEntity<>(result, HttpStatus.OK);
      } else {
         response = new ResponseEntity<>("Fail", HttpStatus.NOT_FOUND);
      }

      return response;
   }
   
   //재접속시 로그인 상태 유지
   @GetMapping("/account/loginSave")
   @ApiOperation(value = "재접속시 로그인상태 유지")
   public Object loginSave(@RequestParam(required = true) final String email) {

      Optional<User> userOpt = accountRepository.findUserByUserEmail(email);
      ResponseEntity response = null;
      if (userOpt.isPresent()) {
    	 Map<String,Object> result = new HashMap<>();
         String token = getToken(email);
         result.put("status", true);
         result.put("data",token);
//            String token = getToken(userOpt);
         result.put("object", userOpt);
         System.out.println(userOpt);
         response = new ResponseEntity<>(result, HttpStatus.OK);
      } else {
         response = new ResponseEntity<>("Fail", HttpStatus.NOT_FOUND);
      }

      return response;
   }

   @GetMapping("/account/signup/valid")
   @ApiOperation(value = "닉네임, 이메일 중복체크")
   public Object nicknameAndEmailVaildCheck(@RequestParam(required = true) final String nickname,@RequestParam(required = true) final String email) {
      User originUser = null;
      Optional<User> originUserEmailCheck = Optional.empty();
      
      Map<String,Object> result = new HashMap<>();
      boolean isExistNickname = false;
      boolean isExistEmail = false;

      // 이메일 중복 검사
      originUser = accountRepository.getUserByUserEmail(email);
      if (originUser != null)
         isExistEmail = true;

      // 닉네임 중복 검사
      // dao에서 optional로 바꿔서 다른변수 만들어줌 이부분
      originUserEmailCheck = accountRepository.getUserByUserNickname(nickname);
      // ispresent하면 null인지 아닌지 확인가능
      if (originUserEmailCheck.isPresent())
         isExistNickname = true;
      
      
      // 기존에 가입한 동일 닉네임이 있다면
      if (isExistNickname) {
         result.put("status", false);
         result.put("data", "isExistNickname");
      }
      // 기존에 가입한 동일 이메일이 있다면
      else if (isExistEmail) {
    	 result.put("status", false);
         result.put("data", "isExistEmail");
      }

      // 기존 유저가 없다면
      else {
    	 result.put("status", false);
         result.put("data", "success");
      }
      return new ResponseEntity<>(result, HttpStatus.OK);
   }  
   @PostMapping("/account/apisignup")
   @ApiOperation(value = "[가입]api가입하기")
   public Object kakaosignup(@RequestBody User request) {
	  Map<String,Object> result = new HashMap<>();
      System.out.println(request);
      // user entity db 저장

      accountRepository.save(request);
      result.put("status", true);
      result.put("data", "success");
      return new ResponseEntity<>(result, HttpStatus.OK);
   }

   static Signer signer = HMACSigner.newSHA256Signer("123");

   static public String getToken(String email) {
      // UserEmail로 토큰을 만든다.
      // plusMinutes 는 토큰을 등록하는 시간임 지금은 1분
      JWT jwt = new JWT().setIssuer(email).setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
            .setSubject("f1e33ab3-027f-47c5-bb07-8dd8ab37a2d3")
            .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(1));
      // Sign and encode the JWT to a JSON string representation
      String token = JWT.getEncoder().encode(jwt, signer);
      return token;
   }

   // 복호화 하는 방법 : 내이름 넣음
   // 토큰이 필요한 API 정보에 대해서 유효성을 체크해주면된다
   static Verifier verifier = HMACVerifier.newVerifier("123");

   static public boolean cmpToken(String token) {
      try {
         // Build an HMC verifier using the same secret that was used to sign the JWT
         JWT jwt = JWT.getDecoder().decode(token, verifier);
         if(jwt.subject.equals("f1e33ab3-027f-47c5-bb07-8dd8ab37a2d3")) {
        	 return true;
         }
      } catch (Exception e) {
         return false;
      }
      return true;
   }

}