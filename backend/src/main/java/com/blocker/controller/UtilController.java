package com.blocker.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.dto.LikeBoardDto;
import com.blocker.dto.Member;
import com.blocker.repository.MemberRepository;
import com.blocker.request.CreateLikeRequest;
import com.blocker.service.CommentBoardService;
import com.blocker.service.LikeBoardService;
import com.blocker.util.BasicResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/util")
public class UtilController {

	@Autowired
	LikeBoardService likeBoardService;
	@Autowired
	CommentBoardService commentBoardService;
	@Autowired
	MemberRepository memberRepository;

	@PostMapping("/createlike")
	@ApiOperation(value = "좋아요 클릭 했을경우 object값이 1이면 성공 0이면 실패임")
	public Object createlike(@RequestBody CreateLikeRequest likeRequest) {
		final BasicResponse result = new BasicResponse();
		Map<String, Integer> map = new HashMap<>();
		try {

			Optional<LikeBoardDto> oplikeBoardDto = likeBoardService.findLike(likeRequest);
			// 이미 존재 한다면 boolean값을 보고 업데이트해줌
			if (oplikeBoardDto.isPresent()) {
				LikeBoardDto updatelikeBoardDto = oplikeBoardDto.get();

				if (updatelikeBoardDto.isIschecked()) {
					System.out.println("true=>false");
					updatelikeBoardDto.setIschecked(false);
					map.put("likestate", 0);
				} else {
					System.out.println("false=>true");
					updatelikeBoardDto.setIschecked(true);
					map.put("likestate", 1);
				}

				likeBoardService.updateLike(updatelikeBoardDto);
				map.put("likecount", likeBoardService.likeCount(likeRequest.getAddress()));
				result.object = map;
				result.data = "success";
				result.status = true;
			} else {// 그게 아니라면 true값으로 초기화 해줌
				LikeBoardDto likeboardDto = new LikeBoardDto();
				likeboardDto.setAddress(likeRequest.getAddress());
				likeboardDto.setIschecked(true);
				likeboardDto.setUserid(likeRequest.getUserid());
				likeboardDto = likeBoardService.CreateLike(likeboardDto);
				map.put("likestate", 1);
				map.put("likecount", likeBoardService.likeCount(likeRequest.getAddress()));
				if (likeboardDto != null) {
					result.object = map;
					result.data = "success";
					result.status = true;
				} else {
					System.out.println("save도중에 애러가 발생함");
					result.object = null;
					result.data = "sql error";
					result.status = false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("좋아요 true로 하던도중 애러발생");
			result.data = "fail";
			result.object = null;
			result.status = true;
		} finally {

			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@PostMapping("/userinfo")
	@ApiOperation(value = "유저 정보를 리턴해줌")
	public Object userinfo(@RequestParam String userid) {
		final BasicResponse result = new BasicResponse();

		try {
			Member member = memberRepository.findByOauthId(userid);
			result.object = member;
			result.data = "success";
			result.status = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("유저정보를 가져오던 도중 오류가 났습니다");
			result.data = "fail";
			result.status = true;
		} finally {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

}
