package com.blocker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.dto.BlockTransaction;
import com.blocker.dto.InvestmentDto;
import com.blocker.dto.LikeBoardDto;
import com.blocker.dto.SaleBoardDto;
import com.blocker.service.InvestmentService;
import com.blocker.service.LikeBoardService;
import com.blocker.service.MyPageService;
import com.blocker.service.MySellPList;
import com.blocker.service.SaleService;
import com.blocker.util.BasicResponse;
import com.blocker.util.PageRequest;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/mypage")
public class MypageController {
	@Autowired
	MyPageService myPageService;
	@Autowired
	InvestmentService investmentService;
	@Autowired
	SaleService saleService;
	@Autowired
	LikeBoardService likeBoardService;

	@GetMapping("/myproject")
	@ApiOperation(value = "[myproject] 내가 생성,참여한 프로젝트 횟수를 보여줍니다.(투자, 쇼핑)")
	public ResponseEntity<Map<String, String>> myproject(@RequestParam("oauthId") String oauthId) throws Exception {
		return new ResponseEntity<Map<String, String>>(myPageService.myProject(oauthId), HttpStatus.OK);
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
	@ApiOperation(value = "[mypage-쇼핑] 내가 참여한 쇼핑 프로젝트를 가져옵니다.")
	@GetMapping("/saleplist")
	public List<MySellPList> getsalelist(@RequestParam("oauthId") String oauthId, PageRequest pageable) {
		return myPageService.getsaleList(oauthId, pageable.of());
	}

	@PostMapping("/likelist")
	@ApiOperation(value = "[mypage-찜한목록] like누른 프로젝트를 전부 가져옵니다.")
	public Object likelist(@RequestParam String userid) {
		BasicResponse result = new BasicResponse();
		List<InvestmentDto> investmentList = new ArrayList<>();
		List<SaleBoardDto> saleboardList = new ArrayList<>();
		List<String> saleOneLineIntro = new ArrayList<>();

		Map<String, Object> map = new HashMap<>();
		try {
			List<LikeBoardDto> allProjectList = likeBoardService.getAllProjectILike(userid);

			for (Iterator<LikeBoardDto> iter = allProjectList.iterator(); iter.hasNext();) {
				LikeBoardDto likeBoardDto = iter.next();
				Optional<InvestmentDto> opinvestmentDto = investmentService.getInvestment(likeBoardDto.getAddress());
				if (opinvestmentDto.isPresent()) {
					investmentList.add(opinvestmentDto.get());
					
				} else {
					SaleBoardDto saleboardDto = saleService.getSaleBoard(likeBoardDto.getAddress()).get();
					saleboardList.add(saleboardDto);
					saleOneLineIntro.add(
							investmentService.getInvestment(saleboardDto.getInvestaddress()).get().getOnelineintro());
				}
			}
			
			map.put("investmentList", investmentList);
			map.put("saleboardList", saleboardList);
			map.put("saleonelineIntro", saleOneLineIntro);

			result.object = map;
			result.data = "success";
			result.status = true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("찜한 목록 error");
			result.data = "fail";
			result.status = true;
		} finally {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
}
