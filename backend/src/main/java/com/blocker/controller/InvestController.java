package com.blocker.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blocker.dto.BoardCategoryDto;
import com.blocker.dto.BoardTagDto;
import com.blocker.dto.CategoryDto;
import com.blocker.dto.CommentBoardDto;
import com.blocker.dto.EditorInvestmentDto;
import com.blocker.dto.InvestmentDto;
import com.blocker.dto.LikeBoardDto;
import com.blocker.dto.SaleBoardDto;
import com.blocker.dto.TagDto;
import com.blocker.repository.BoardCategoryMapping;
import com.blocker.repository.BoardCategoryRepository;
import com.blocker.repository.BoardTagRepository;
import com.blocker.repository.CategoryRepository;
import com.blocker.repository.EditorInvestmentRepository;
import com.blocker.repository.InvestmentRepository;
import com.blocker.repository.TagRepository;
import com.blocker.repository.getAllMyPjtResponse;
import com.blocker.request.CreateCommentRequest;
import com.blocker.request.CreateLikeRequest;
import com.blocker.request.InvestmentRequest;
import com.blocker.request.InvestmentResponse;
import com.blocker.service.BoardCategoryService;
import com.blocker.service.CommentBoardService;
import com.blocker.service.InvestmentService;
import com.blocker.service.LikeBoardService;
import com.blocker.service.SaleService;
import com.blocker.util.BasicResponse;
import com.blocker.util.webhook;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/investment")
public class InvestController {

	@Autowired
	private InvestmentRepository investmentRepository;
	@Autowired
	private InvestmentService investmentService;
	@Autowired
	private EditorInvestmentRepository editorinvestmentRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private BoardTagRepository boardTagRepository;
	@Autowired
	private BoardCategoryRepository boardCategoryRepository;
	@Autowired
	private BoardCategoryService boardCategoryService;

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private SaleService saleService;
	@Autowired
	CommentBoardService commentBoardService;

	@Autowired
	LikeBoardService likeBoardService;

	@PostMapping("/create")
	@ApiOperation(value = "투자 게시글 생성")
	public Object createInvestment(@RequestBody InvestmentRequest pinvestment) {
		final BasicResponse result = new BasicResponse();
		try {
			InvestmentDto investmentdto = new InvestmentDto();

			System.out.println("userid==========>" + pinvestment.getUserid());
			UUID uuid = UUID.randomUUID();
			investmentdto.setAddress(uuid.toString());
			investmentdto.setUserid(pinvestment.getUserid());
			investmentdto.setCompname(pinvestment.getCompName());
			investmentdto.setDeadline(pinvestment.getDeadLine());
			investmentdto.setExpectedsaleprice(pinvestment.getExpectedSalePrice());
			investmentdto.setGoalprice(pinvestment.getGoalPrice());
			investmentdto.setIdentity(pinvestment.getIdentity());
			investmentdto.setIntroduce(pinvestment.getIntroduce());
			investmentdto.setOnelineintro(pinvestment.getOneLineIntro());
			investmentdto.setPicture(pinvestment.getPicture());
			investmentdto.setPjtname(pinvestment.getPjtName());
			investmentdto.setUrl(pinvestment.getUrl());
			investmentdto.setIsfinish(false);// true면 이미 종료된 투자

			InvestmentDto tempInvestmentDto = investmentRepository.save(investmentdto);

			// editor값 넣기
			EditorInvestmentDto editorInvestmentDto = new EditorInvestmentDto();
			editorInvestmentDto.setEditorhtml(pinvestment.getEditorhtml());
			editorInvestmentDto.setInvestaddress(tempInvestmentDto.getAddress());
			editorinvestmentRepository.save(editorInvestmentDto);

			// tag
			List<String> tags = pinvestment.getTags();

			for (Iterator<String> iter = tags.iterator(); iter.hasNext();) {
				String tag = iter.next();
				Optional<TagDto> optagdto = tagRepository.findTagDtoByTagname(tag);
				TagDto tagdto;
				if (!optagdto.isPresent()) {
					tagdto = new TagDto();
					tagdto.setTagname(tag);
					tagdto = tagRepository.save(tagdto);
				} else {
					tagdto = new TagDto(optagdto.get());
				}
				System.out.println("tagdto==>" + tagdto.toString());
				BoardTagDto boardTagDto = new BoardTagDto();
				boardTagDto.setInvestaddress(tempInvestmentDto.getAddress());
				boardTagDto.setTagname(tagdto.getTagname());
				boardTagRepository.save(boardTagDto);
			}

			// category
			List<String> categorys = pinvestment.getCategorys();

			for (Iterator<String> iter = categorys.iterator(); iter.hasNext();) {
				String category = iter.next();
				Optional<CategoryDto> opCategorydto = categoryRepository.findCategoryDtoByCategoryname(category);
				CategoryDto categoryDto;
				if (!opCategorydto.isPresent()) {
					categoryDto = new CategoryDto();
					categoryDto.setCategoryname(category);
					categoryDto = categoryRepository.save(categoryDto);
				} else {
					categoryDto = new CategoryDto(opCategorydto.get());
				}
				System.out.println("categorydto==>" + categoryDto.toString());
				BoardCategoryDto boardCategoryDto = new BoardCategoryDto();
				boardCategoryDto.setCategoryname(category);
				boardCategoryDto.setInvestaddress(tempInvestmentDto.getAddress());
				boardCategoryRepository.save(boardCategoryDto);
			}

			result.object = tempInvestmentDto;
			result.data = "Success";
			result.status = true;
		} catch (Exception e) {
			result.data = "fail";
			result.status = false;
			result.object = null;

			System.out.println("투자 게시글 생성중 오류 발생");
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@PostMapping("/changePath")
	@ApiOperation(value = "사진을 받고 경로명을 바꾸어서 보내줌")
	@ResponseBody
	public String changePath(@RequestBody MultipartFile img) {
		Map<String, Object> resultMap = new HashMap<>();
		final BasicResponse result = new BasicResponse();
		// 이 path는 나중에 서버경로로 바꿔줘야함
		String path = "/home/ubuntu/apps/upload/image/";
		UUID uuid = UUID.randomUUID();
		String savedName = uuid.toString() + "_" + img.getOriginalFilename();
		File file = new File(path + savedName);
		try {
			// 경로바꾸고 이거 주석 풀어줘야함
			img.transferTo(file);
		} catch (Exception e) {
			System.out.println("이미지 저장중 오류발생");
			e.printStackTrace();
		} finally {
			return file.getAbsolutePath();
		}
	}

	@GetMapping("/investList/{page}")
	@ApiOperation(value = "저장되어있는 나의 모든 투자 게시글을보여줌")
	@ResponseBody
	public Object investMyList(@RequestParam String userid, @PathVariable int page) {
		final BasicResponse result = new BasicResponse();
		Page<InvestmentDto> list;
		List<InvestmentResponse> resultDatas = new ArrayList<>();

		list = investmentService.getAllMyinvestmentList(userid, page);
		try {
			for (Iterator<InvestmentDto> iter = list.getContent().iterator(); iter.hasNext();) {
				InvestmentDto investmentDto = iter.next();
				InvestmentResponse investmentResponse = new InvestmentResponse();
				investmentResponse.setAddress(investmentDto.getAddress());
				investmentResponse.setCompName(investmentDto.getCompname());
				investmentResponse.setDeadLine(investmentDto.getDeadline());
				investmentResponse.setExpectedSalePrice(investmentDto.getExpectedsaleprice());
				investmentResponse.setGoalPrice(investmentDto.getGoalprice());
				investmentResponse.setIdentity(investmentDto.getIdentity());
				investmentResponse.setIntroduce(investmentDto.getIntroduce());
				investmentResponse.setOneLineIntro(investmentDto.getOnelineintro());
				investmentResponse.setPicture(investmentDto.getPicture());
				investmentResponse.setPjtName(investmentDto.getPjtname());
				investmentResponse.setUrl(investmentDto.getUrl());
				investmentResponse.setUserid(investmentDto.getUserid());
				investmentResponse.setIsfinish(investmentDto.isIsfinish());
				investmentResponse.setTotalpage(list.getTotalPages());
				// editor
				Optional<EditorInvestmentDto> opEditorInvestmentDto = editorinvestmentRepository
						.getEditorInvestmentDtoByInvestaddress(investmentDto.getAddress());
				if (opEditorInvestmentDto.isPresent()) {
					investmentResponse.setEditorhtml(opEditorInvestmentDto.get().getEditorhtml());
				}
				resultDatas.add(investmentResponse);
			}

			result.data = "success";
			result.object = resultDatas;
			result.status = true;
		} catch (Exception e) {
			result.data = "fail";
			result.object = null;
			result.status = false;
		} finally {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@PostMapping("/getAllInvestBoard/{page}")
	@ApiOperation(value = "저장되어있는 모든사람의 투자 게시글을보여줌 orderOption = 0전체 1최신 2 인기")
	@ResponseBody
	public Object getAllInvestBoard(@PathVariable int page, @RequestParam List<String> categoryfilter,
			@RequestParam int orderOption) {
		final BasicResponse result = new BasicResponse();
		List<InvestmentResponse> resultDatas = new ArrayList<>();

		try {
			System.out.println(categoryfilter.isEmpty());
			if (categoryfilter.isEmpty()) {
				Page<InvestmentDto> list = null;
				if (orderOption == 0) {
					System.out.println(0);
					list = investmentService.getAllInvestmentList(page);
				} else {
					list = investmentService.getAllInvestmentList(page, orderOption);
					System.out
							.println("=============================================================" + list.toString());
				}

				for (Iterator<InvestmentDto> iter = list.getContent().iterator(); iter.hasNext();) {
					InvestmentDto investmentDto = iter.next();
					InvestmentResponse investmentResponse = new InvestmentResponse();
					investmentResponse.setAddress(investmentDto.getAddress());
					investmentResponse.setCompName(investmentDto.getCompname());
					investmentResponse.setDeadLine(investmentDto.getDeadline());
					investmentResponse.setExpectedSalePrice(investmentDto.getExpectedsaleprice());
					investmentResponse.setGoalPrice(investmentDto.getGoalprice());
					investmentResponse.setIdentity(investmentDto.getIdentity());
					investmentResponse.setIntroduce(investmentDto.getIntroduce());
					investmentResponse.setOneLineIntro(investmentDto.getOnelineintro());
					investmentResponse.setPicture(investmentDto.getPicture());
					investmentResponse.setPjtName(investmentDto.getPjtname());
					investmentResponse.setUrl(investmentDto.getUrl());
					investmentResponse.setUserid(investmentDto.getUserid());
					investmentResponse.setIsfinish(investmentDto.isIsfinish());
					investmentResponse.setTotalpage(list.getTotalPages());

					// editor
					Optional<EditorInvestmentDto> opEditorInvestmentDto = editorinvestmentRepository
							.getEditorInvestmentDtoByInvestaddress(investmentDto.getAddress());
					if (opEditorInvestmentDto.isPresent()) {
						investmentResponse.setEditorhtml(opEditorInvestmentDto.get().getEditorhtml());
					}
					resultDatas.add(investmentResponse);
				}
			} else {
				Page<BoardCategoryMapping> list = boardCategoryService.getAllInvestmentListWithCategory(page,
						categoryfilter, orderOption);

				for (Iterator<BoardCategoryMapping> iter = list.iterator(); iter.hasNext();) {
					BoardCategoryMapping nextiter = iter.next();
					String investaddress = nextiter.getInvestaddress();
					Optional<InvestmentDto> investmentDto = investmentService.getInvestment(investaddress);
					if (investmentDto.isPresent()) {
						InvestmentResponse investmentResponse = new InvestmentResponse();
						investmentResponse.setAddress(investmentDto.get().getAddress());
						investmentResponse.setCompName(investmentDto.get().getCompname());
						investmentResponse.setDeadLine(investmentDto.get().getDeadline());
						investmentResponse.setExpectedSalePrice(investmentDto.get().getExpectedsaleprice());
						investmentResponse.setGoalPrice(investmentDto.get().getGoalprice());
						investmentResponse.setIdentity(investmentDto.get().getIdentity());
						investmentResponse.setIntroduce(investmentDto.get().getIntroduce());
						investmentResponse.setOneLineIntro(investmentDto.get().getOnelineintro());
						investmentResponse.setPicture(investmentDto.get().getPicture());
						investmentResponse.setPjtName(investmentDto.get().getPjtname());
						investmentResponse.setUrl(investmentDto.get().getUrl());
						investmentResponse.setUserid(investmentDto.get().getUserid());
						investmentResponse.setIsfinish(investmentDto.get().isIsfinish());
						investmentResponse.setTotalpage(list.getTotalPages());

						// editor
						Optional<EditorInvestmentDto> opEditorInvestmentDto = editorinvestmentRepository
								.getEditorInvestmentDtoByInvestaddress(investmentDto.get().getAddress());
						if (opEditorInvestmentDto.isPresent()) {
							investmentResponse.setEditorhtml(opEditorInvestmentDto.get().getEditorhtml());
						}
						resultDatas.add(investmentResponse);
					}
				}
			}
			result.data = "success";
			result.object = resultDatas;
			result.status = true;
		} catch (Exception e) {
			result.data = "fail";
			result.object = null;
			result.status = false;
		} finally {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@PostMapping("/getDetail")
	@ApiOperation(value = "투자게시글에대한 디테일 페이지")
	public Object getDetail(@RequestParam String address, @RequestParam String userid) {
		final BasicResponse result = new BasicResponse();
		Map<String, Object> map = new HashMap<>();
		try {
			Optional<InvestmentDto> opInvestmentDto = investmentService.getInvestment(address);
			if (opInvestmentDto.isPresent()) {
				InvestmentDto investmentDto = opInvestmentDto.get();
				// tags
				List<BoardTagDto> temptags = boardTagRepository.findAllBoardTagDtoByInvestaddress(address);
				List<String> tags = new ArrayList<>();
				for (Iterator<BoardTagDto> iter = temptags.iterator(); iter.hasNext();) {
					BoardTagDto boardTagDto = iter.next();
					tags.add(boardTagDto.getTagname());
				}

				// category
				List<BoardCategoryDto> tempcategorys = boardCategoryRepository
						.findAllBoardCategoryDtoByAddress(address);
				List<String> categorys = new ArrayList<>();
				for (Iterator<BoardCategoryDto> iter = tempcategorys.iterator(); iter.hasNext();) {
					BoardCategoryDto boardCategoryDto = iter.next();
					categorys.add(boardCategoryDto.getCategoryname());
				}

				// comments
				List<CommentBoardDto> comments = commentBoardService.getAllComment(address);
				map.put("commentsCount", comments.size());
				InvestmentRequest data = new InvestmentRequest(categorys, tags, comments);
				data.setCompName(investmentDto.getCompname());
				data.setDeadLine(investmentDto.getDeadline());
				data.setExpectedSalePrice(investmentDto.getExpectedsaleprice());
				data.setGoalPrice(investmentDto.getGoalprice());
				data.setIdentity(investmentDto.getIdentity());
				data.setIntroduce(investmentDto.getIntroduce());
				data.setOneLineIntro(investmentDto.getOnelineintro());
				data.setPicture(investmentDto.getPicture());
				data.setPjtName(investmentDto.getPjtname());
				data.setUserid(investmentDto.getUserid());
				data.setUrl(investmentDto.getUrl());
				data.setEditorhtml(editorinvestmentRepository.getEditorInvestmentDtoByInvestaddress(address).get()
						.getEditorhtml());
				map.put("object", data);
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
				result.object = map;
				result.data = "success";
				result.status = true;
			} else {
				result.object = null;
				result.data = "fail";
				result.status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.object = null;
			result.data = "fail";
			result.status = false;
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/getAllPJT/{page}")
	@ApiOperation(value = "금손의 모든 프로젝트를 가져옴")
	public Object getAllMyPjt(@RequestParam String userid, @PathVariable int page) {
		final BasicResponse result = new BasicResponse();
		try {
			Page<InvestmentDto> pageInvestment = investmentService.getAllMyinvestmentListForGetAllPJT(userid, page);
			List<InvestmentDto> templist = pageInvestment.getContent();
			List<getAllMyPjtResponse> resultdatas = new ArrayList<>();
			for (Iterator<InvestmentDto> iter = templist.iterator(); iter.hasNext();) {
				InvestmentDto pinvestmentdDto = iter.next();
				System.out.println("address=====>" + pinvestmentdDto.getAddress());
				Optional<SaleBoardDto> opsaleBoardDto = saleService
						.getSaleBoardByInvestAddress(pinvestmentdDto.getAddress());
				System.out.println("opsaleBoardDto=====>" + opsaleBoardDto.isPresent());
				if (opsaleBoardDto.isPresent()) {
					System.out.println(opsaleBoardDto.get().toString());
					getAllMyPjtResponse data = new getAllMyPjtResponse(pinvestmentdDto, opsaleBoardDto.get(),
							pageInvestment.getTotalPages());
					resultdatas.add(data);
				} else {
					System.out.println(pageInvestment.getTotalPages());
					getAllMyPjtResponse data = new getAllMyPjtResponse(pinvestmentdDto, pageInvestment.getTotalPages());
					resultdatas.add(data);
				}

			}

			result.data = "success";
			result.object = resultdatas;
			result.status = true;

		} catch (Exception e) {
			e.printStackTrace();
			result.data = "fail";
			result.object = null;
			result.status = true;
		} finally {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@PostMapping("/createcomment")
	@ApiOperation(value = "댓글쓰기")
	public Object createcomment(@RequestBody CreateCommentRequest pcreateCommentRequest) {
		final BasicResponse result = new BasicResponse();
		try {
			CommentBoardDto commentBoardDto = new CommentBoardDto(pcreateCommentRequest);
			commentBoardDto = commentBoardService.createComment(commentBoardDto);
			result.object = commentBoardDto;
			result.data = "success";
			result.status = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("댓글생성중 오류 발생");
			result.object = null;
			result.data = "fail";
			result.status = true;
		} finally {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@DeleteMapping("/deletecomment")
	@ApiOperation(value = "댓글 삭제")
	public void deleteComment(@RequestParam int num) {
		Map<String, Object> map = new HashMap<>();
		try {
			int data = commentBoardService.deleteComment(num);
			System.out.println("data====>" + data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("댓글 삭제 도중 error발생");
		}
	}

	@GetMapping("/curation")
	@ApiOperation(value = "마감임박과 인기순 3개씩 보내주는 함수")
	public Object curation() {
		final BasicResponse result = new BasicResponse();
		Map<String, Object> map = new HashMap<>();
		try {
			List<InvestmentDto> popular = investmentService.getThreeInvestmentListOrderbyLikecount();
			List<InvestmentDto> closedeadlines = investmentService.getThreeInvestmentListOrderbyDeadline();
			List<Integer> likecount = new ArrayList<>();
			List<Integer> closedeadlineLikecount = new ArrayList<>();
			map.put("closedeadlines", closedeadlines);
			map.put("popular", popular);
			for (Iterator<InvestmentDto> iter = popular.iterator(); iter.hasNext();) {
				InvestmentDto investmentDto = iter.next();
				likecount.add(likeBoardService.likeCount(investmentDto.getAddress()));
			}
			for (Iterator<InvestmentDto> iter = closedeadlines.iterator(); iter.hasNext();) {
				InvestmentDto investmentDto = iter.next();
				closedeadlineLikecount.add(likeBoardService.likeCount(investmentDto.getAddress()));
			}
			map.put("closedeadlinelikecount", closedeadlineLikecount);
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
		System.err.println("invest 부분에서 " + e.getClass());
		webhook w = new webhook();
		w.send("invest 부분에서 " + e.getClass());
	}
}
