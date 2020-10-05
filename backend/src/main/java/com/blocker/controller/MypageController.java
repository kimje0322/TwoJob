package com.blocker.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.dto.BlockTransaction;
import com.blocker.service.MyPageService;
import com.blocker.util.PageRequest;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/mypage")
public class MypageController {
	@Autowired
	MyPageService myPageService;
	
	@GetMapping("/myproject")
    @ApiOperation(value = "[myproject] 내가 생성,참여한 프로젝트 횟수를 보여줍니다.(투자, 쇼핑)")
    public ResponseEntity<Map<String,String>> myproject(@RequestParam("oauthId") String oauthId) throws Exception {
    	return new ResponseEntity<Map<String,String>>(myPageService.myProject(oauthId),HttpStatus.OK);
    }
	@ApiOperation(value = "[mypage-투자] 마이페이지의 투자 탭의 transaction histroy를 가져옵니다.")
	@GetMapping("/tojalist")
	public Page<BlockTransaction> tojalist(@RequestParam("oauthId") String oauthId, PageRequest pageable) {
		return myPageService.tojalist(oauthId, pageable.of());
	}
	@ApiOperation(value = "[mypage-쇼핑] 마이페이지의 쇼핑 탭의 transaction histroy를 가져옵니다.")
	@GetMapping("/salelist")
	public Page<BlockTransaction> salelist(@RequestParam("oauthId") String oauthId, PageRequest pageable) {
		return myPageService.salelist(oauthId, pageable.of());
	}
}
