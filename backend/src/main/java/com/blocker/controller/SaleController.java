package com.blocker.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.dto.BoardCategoryDto;
import com.blocker.dto.BoardTagDto;
import com.blocker.dto.CommentBoardDto;
import com.blocker.dto.EditorSaleDto;
import com.blocker.dto.InvestmentDto;
import com.blocker.dto.LikeBoardDto;
import com.blocker.dto.ReviewDto;
import com.blocker.dto.SaleBoardDto;
import com.blocker.repository.BoardCategoryMapping;
import com.blocker.repository.BoardCategoryRepository;
import com.blocker.repository.BoardTagRepository;
import com.blocker.repository.EditorSaleRepository;
import com.blocker.repository.InvestmentRepository;
import com.blocker.repository.ReviewRepository;
import com.blocker.repository.SaleBoardRepository;
import com.blocker.repository.TagRepository;
import com.blocker.request.CreateCommentRequest;
import com.blocker.request.CreateLikeRequest;
import com.blocker.request.ReviewsResponse;
import com.blocker.request.SaleBoardRequest;
import com.blocker.request.SaleBoardResponse;
import com.blocker.request.SaleBoardReviewRequest;
import com.blocker.request.SaleDetailResponse;
import com.blocker.service.BoardCategoryService;
import com.blocker.service.CommentBoardService;
import com.blocker.service.EditorSaleService;
import com.blocker.service.InvestmentService;
import com.blocker.service.LikeBoardService;
import com.blocker.service.ReviewService;
import com.blocker.service.SaleService;
import com.blocker.util.BasicResponse;
import com.blocker.util.webhook;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/sale")
public class SaleController {

	@Autowired
	SaleService saleService;
	@Autowired
	EditorSaleService editorSaleService;
	@Autowired
	SaleBoardRepository saleBoardRepository;
	@Autowired
	EditorSaleRepository editorSaleRepository;
	@Autowired
	InvestmentRepository investmentRepository;
	@Autowired
	ReviewService reviewService;
	@Autowired
	InvestmentService investmentService;
	@Autowired
	BoardCategoryRepository boardCategoryRepository;
	@Autowired
	BoardTagRepository boardTagRepository;
	@Autowired
	BoardCategoryService boardCategoryService;
	@Autowired
	LikeBoardService likeBoardService;

	@PostMapping("/create")
	@ApiOperation(value = "판매 게시글 생성")
	public Object createSale(@RequestBody SaleBoardRequest saleBoardRequest) {
		final BasicResponse result = new BasicResponse();
		try {
			SaleBoardDto saleBoardDto = new SaleBoardDto(saleBoardRequest);
			saleBoardDto.setAddress(UUID.randomUUID().toString());
			saleBoardDto.setIsfinish(false);
			SaleBoardDto tempSaleBoardDto = saleBoardRepository.save(saleBoardDto);

			// editor에 값넣기
			EditorSaleDto editorSaleDto = new EditorSaleDto();
			editorSaleDto.setEditorhtml(saleBoardRequest.getEditorhtml());
			editorSaleDto.setAddress(saleBoardDto.getAddress());
			editorSaleRepository.save(editorSaleDto);
			result.data = saleBoardDto.getAddress();
			
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

	@GetMapping("/saleList/{page}")
	@ApiOperation(value = "내가 올린 판매 게시글 리스트 가져오기")
	public Object saleMyList(@RequestParam String userid, @PathVariable int page) {
		final BasicResponse result = new BasicResponse();
		Map<String, Object> map = new HashMap<>();
		List<SaleBoardResponse> resultDatalist = new ArrayList<>();
		List<String> oneLineIntroList = new ArrayList<>();
		List<Integer> likeCount = new ArrayList<>();
		Page<SaleBoardDto> list = saleService.getAllMySaleList(userid, page);

		try {
			for (Iterator<SaleBoardDto> iter = list.getContent().iterator(); iter.hasNext();) {
				SaleBoardDto saleBoardDto = iter.next();
				EditorSaleDto editorSaleDto = editorSaleRepository
						.findEditorSaleDtoByAddress(saleBoardDto.getAddress());
				SaleBoardResponse data = new SaleBoardResponse();
				if (!editorSaleDto.getEditorhtml().isEmpty()) {
					data.setEditorhtml(editorSaleDto.getEditorhtml());
				}
				data.setAddress(saleBoardDto.getAddress());
				data.setInvestaddress(saleBoardDto.getInvestaddress());
				data.setPicture(saleBoardDto.getPicture());
				data.setPjtname(saleBoardDto.getPjtname());
				data.setSaleprice(saleBoardDto.getSaleprice());
				data.setStartdate(saleBoardDto.getStartdate());
				data.setUrl(saleBoardDto.getUrl());
				data.setUserid(saleBoardDto.getUserid());
				data.setIsfinish(saleBoardDto.isIsfinish());
				resultDatalist.add(data);

				// onelineintro
				oneLineIntroList
						.add(investmentService.getInvestment(saleBoardDto.getInvestaddress()).get().getOnelineintro());

				// likecount
				likeCount.add(likeBoardService.likeCount(saleBoardDto.getAddress()));
			}
			map.put("object", resultDatalist);
			map.put("onelineintros", oneLineIntroList);
			map.put("likecount", likeCount);
			map.put("totalpage", list.getTotalPages());
			result.data = "success";
			result.object = map;
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

	@PostMapping("/getAllSaleList/{page}")
	@ApiOperation(value = "저장되어있는 모든사람의 판매 게시글을보여줌 orderOption = 0전체 1최신 2 인기")
	public Object getAllSaleList(@PathVariable int page, @RequestParam List<String> categoryfilter,
			@RequestParam int orderOption) {
		final BasicResponse result = new BasicResponse();
		Map<String, Object> map = new HashMap<>();
		List<SaleBoardResponse> resultDatalist = new ArrayList<>();
		List<Integer> likeCount = new ArrayList<>();
		try {
			if (categoryfilter.isEmpty()) {
				Page<SaleBoardDto> list = null;
				if (orderOption == 0) {
					list = saleService.getAllSaleList(page);
				} else {
					list = saleService.getAllSaleList(page, orderOption);
				}
				for (Iterator<SaleBoardDto> iter = list.getContent().iterator(); iter.hasNext();) {
					SaleBoardDto saleBoardDto = iter.next();
					EditorSaleDto editorSaleDto = editorSaleRepository
							.findEditorSaleDtoByAddress(saleBoardDto.getAddress());
					InvestmentDto investmentDto = investmentService.getInvestment(saleBoardDto.getInvestaddress())
							.get();
					SaleBoardResponse data = new SaleBoardResponse();
					if (!editorSaleDto.getEditorhtml().isEmpty()) {
						data.setEditorhtml(editorSaleDto.getEditorhtml());
					}
					data.setAddress(saleBoardDto.getAddress());
					data.setInvestaddress(saleBoardDto.getInvestaddress());
					data.setPicture(saleBoardDto.getPicture());
					data.setPjtname(saleBoardDto.getPjtname());
					data.setSaleprice(saleBoardDto.getSaleprice());
					data.setStartdate(saleBoardDto.getStartdate());
					data.setUrl(saleBoardDto.getUrl());
					data.setUserid(saleBoardDto.getUserid());
					data.setIsfinish(saleBoardDto.isIsfinish());
					data.setCompname(investmentDto.getCompname());
					data.setOnelineintro(investmentDto.getOnelineintro());
					data.setTotalpage(list.getTotalPages());
					resultDatalist.add(data);

					// like
					likeCount.add(likeBoardService.likeCount(data.getAddress()));
				}
			} else {
				Page<BoardCategoryMapping> list = boardCategoryService.getAllInvestmentListWithCategory(page,
						categoryfilter, orderOption);
				for (Iterator<BoardCategoryMapping> iter = list.iterator(); iter.hasNext();) {
					BoardCategoryMapping nextiter = iter.next();
					String investaddress = nextiter.getInvestaddress();
					Optional<SaleBoardDto> saleBoardDto = saleBoardRepository
							.findSaleBoardDtoByInvestaddress(investaddress);

					if (saleBoardDto.isPresent()) {
						InvestmentDto investmentDto = investmentService.getInvestment(investaddress).get();
						SaleBoardResponse data = new SaleBoardResponse();
						EditorSaleDto editorSaleDto = editorSaleRepository
								.findEditorSaleDtoByAddress(saleBoardDto.get().getAddress());
						if (!editorSaleDto.getEditorhtml().isEmpty()) {
							data.setEditorhtml(editorSaleDto.getEditorhtml());
						}
						data.setAddress(saleBoardDto.get().getAddress());
						data.setInvestaddress(saleBoardDto.get().getInvestaddress());
						data.setPicture(saleBoardDto.get().getPicture());
						data.setPjtname(saleBoardDto.get().getPjtname());
						data.setSaleprice(saleBoardDto.get().getSaleprice());
						data.setStartdate(saleBoardDto.get().getStartdate());
						data.setUrl(saleBoardDto.get().getUrl());
						data.setUserid(saleBoardDto.get().getUserid());
						data.setIsfinish(saleBoardDto.get().isIsfinish());
						data.setCompname(investmentDto.getCompname());
						data.setOnelineintro(investmentDto.getOnelineintro());
						data.setTotalpage(list.getTotalPages());
						resultDatalist.add(data);

						// like
						likeCount.add(likeBoardService.likeCount(data.getAddress()));
					}
				}
			}

			map.put("object", resultDatalist);
			map.put("likecount", likeCount);
			result.data = "success";
			result.object = map;
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

	@PostMapping("/Review")
	@ApiOperation(value = "리뷰쓰기")
	public Object Review(@RequestBody SaleBoardReviewRequest saleBoardReviewRequest) {
		final BasicResponse result = new BasicResponse();

		try {
			ReviewDto reviewDto = new ReviewDto();
			reviewDto.setReviewexplain(saleBoardReviewRequest.getReviewexplain());
			reviewDto.setPicture(saleBoardReviewRequest.getReviewPicture());
			reviewDto.setSaleaddress(saleBoardReviewRequest.getSaleaddress());
			reviewDto.setSatisfied(saleBoardReviewRequest.getSatisfied());
			reviewDto.setSimilar(saleBoardReviewRequest.getSimilar());
			reviewDto.setUserid(saleBoardReviewRequest.getUserid());

			reviewDto = reviewService.createReview(reviewDto);
			result.data = "success";
			result.object = reviewDto;
			result.status = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("리뷰등록 중 실패하였습니다");
			result.data = "fail";
			result.object = null;
			result.status = false;
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/getDetail")
	@ApiOperation(value = "판매 디테일페이지 가져오기")
	public Object getDetail(@RequestParam String address, @RequestParam String userid) {
		final BasicResponse result = new BasicResponse();
		Map<String, Object> map = new HashMap<>();
		try {
			Optional<SaleBoardDto> opSaleBoardDto = saleService.getSaleBoard(address);
			if (opSaleBoardDto.isPresent()) {
				EditorSaleDto editorSaleDto = editorSaleService.getEditorSaleDto(opSaleBoardDto.get().getAddress());
				InvestmentDto tempInvestmentDto = investmentService
						.getInvestment(opSaleBoardDto.get().getInvestaddress()).get();
				// category
				List<String> categorys = new ArrayList<>();
				List<BoardCategoryDto> tempcategorys = boardCategoryRepository
						.findAllBoardCategoryDtoByAddress(tempInvestmentDto.getAddress());
				for (Iterator<BoardCategoryDto> iter = tempcategorys.iterator(); iter.hasNext();) {
					BoardCategoryDto boardCategoryDto = iter.next();
					categorys.add(boardCategoryDto.getCategoryname());
				}

				// tag
				List<String> tags = new ArrayList<>();
				List<BoardTagDto> temptags = boardTagRepository
						.findAllBoardTagDtoByInvestaddress(tempInvestmentDto.getAddress());
				for (Iterator<BoardTagDto> iter = temptags.iterator(); iter.hasNext();) {
					BoardTagDto boardTagDto = iter.next();
					tags.add(boardTagDto.getTagname());
				}
				SaleDetailResponse saleDetailResponse = new SaleDetailResponse(opSaleBoardDto.get(),
						tempInvestmentDto.getCompname(), tempInvestmentDto.getOnelineintro(), editorSaleDto, tags,
						categorys);

				saleDetailResponse.setUrl(tempInvestmentDto.getUrl());
				saleDetailResponse.setIntroduce(tempInvestmentDto.getIntroduce());
				map.put("object", saleDetailResponse);

				// like
				CreateLikeRequest createLikeRequest = new CreateLikeRequest();
				createLikeRequest.setAddress(address);
				createLikeRequest.setUserid(userid);
				Optional<LikeBoardDto> like = likeBoardService.findLike(createLikeRequest);
				if (like.isPresent()) {
					map.put("like", like.get().isIschecked());
				} else {
					map.put("like", false);
				}
				map.put("likecount", likeBoardService.likeCount(address));

				result.data = "success";
				result.object = map;
				result.status = true;
			} else {
				result.data = "success";
				result.object = null;
				result.status = true;
			}
		} catch (Exception e) {
			System.out.println("판매 디테일을 가져오던도중 애러발생");
			result.data = "fail";
			result.object = null;
			result.status = false;
		} finally {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}

	}

	@PostMapping("/getReviews/{page}")
	@ApiOperation(value = "판매게시글의 리뷰를 보여줌")
	public Object getReviews(@RequestParam String address, @PathVariable int page) {
		final BasicResponse result = new BasicResponse();
		Map<String, Object> map = new HashMap<>();
		try {
			Page<ReviewDto> reviewList = reviewService.getReviews(address, page);
			ReviewsResponse reviewsResponse = new ReviewsResponse(reviewList.getContent(), reviewList.getTotalPages());
			map.put("reviews", reviewList.getContent());
			map.put("totalpage", reviewList.getTotalPages());
			map.put("totalreviewcount", reviewService.getReviewsCount(address));
			result.object = map;
			result.data = "success";
			result.status = true;
		} catch (Exception e) {
			result.data = "fail";
			result.status = true;
			e.printStackTrace();
			System.out.println("리뷰를 가져오기 실패");
		} finally {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@GetMapping("/accuracy")
	@ApiOperation(value = "만족도 유사도 제공")
	public Object getAccuracy(@RequestParam String address) {
		final BasicResponse result = new BasicResponse();
		try {
			double accuracy = 0;
			double satisfy = 0;
			double totalCount = 0;
			List<ReviewDto> reviews = reviewService.getReviews(address);
			Map<String, Double> data = new HashMap<>();
			totalCount = reviews.size();
			data.put("reviewtotalcount", totalCount);
			for (Iterator<ReviewDto> iter = reviews.iterator(); iter.hasNext();) {
				ReviewDto reviewDto = iter.next();

				accuracy += reviewDto.getSimilar();
				satisfy += reviewDto.getSatisfied();
			}
			accuracy = Math.round((accuracy / totalCount) * 10);
			accuracy /= 10;
			data.put("accuracy", accuracy);

			satisfy = Math.round((satisfy / totalCount) * 10);
			satisfy /= 10;
			data.put("satisfy", satisfy);

			result.object = data;
			result.data = "success";
			result.status = true;
		} catch (Exception e) {
			e.printStackTrace();
			result.object = null;
			result.data = "fail";
			result.status = true;
		} finally {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@GetMapping("/curation")
	@ApiOperation(value = "오픈예정과 인기순 3개씩 보내주는 함수")
	public Object curation() {
		final BasicResponse result = new BasicResponse();
		Map<String, Object> map = new HashMap<>();
		try {
			List<SaleBoardDto> popular = saleService.getThreeSaleListOrderbyLikecount();
			List<SaleBoardDto> closeopen = saleService.getThreeSaleListOrderbyStartdate();
			List<Integer> likecount = new ArrayList<>();
			List<Integer> closeopenlikecount = new ArrayList<>();
			List<String> likeonelineintro = new ArrayList<>();
			List<String> closeopenonelineintro = new ArrayList<>();
			map.put("closeopen", closeopen);
			map.put("popular", popular);
			for (Iterator<SaleBoardDto> iter = popular.iterator(); iter.hasNext();) {
				SaleBoardDto saleBoardDto = iter.next();
				likecount.add(likeBoardService.likeCount(saleBoardDto.getAddress()));
				likeonelineintro
						.add(investmentService.getInvestment(saleBoardDto.getInvestaddress()).get().getOnelineintro());
			}
			for (Iterator<SaleBoardDto> iter = closeopen.iterator(); iter.hasNext();) {
				SaleBoardDto saleBoardDto = iter.next();
				closeopenlikecount.add(likeBoardService.likeCount(saleBoardDto.getAddress()));
				closeopenonelineintro
						.add(investmentService.getInvestment(saleBoardDto.getInvestaddress()).get().getOnelineintro());
			}
			map.put("likeonelineintro", likeonelineintro);
			map.put("closeopenonelineintro", closeopenonelineintro);
			map.put("closeopenlikecount", closeopenlikecount);
			map.put("popularlikecount", likecount);
			result.object = map;
			result.data = "success";
			result.status = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("curation error");
			result.object = null;
			result.data = "fail";
			result.status = false;
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
