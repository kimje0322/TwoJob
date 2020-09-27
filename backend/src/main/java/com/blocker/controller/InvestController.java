package com.blocker.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.blocker.dto.EditorInvestmentDto;
import com.blocker.dto.InvestmentDto;
import com.blocker.dto.TagDto;
import com.blocker.repository.BoardCategoryRepository;
import com.blocker.repository.BoardTagRepository;
import com.blocker.repository.CategoryRepository;
import com.blocker.repository.EditorInvestmentRepository;
import com.blocker.repository.InvestmentRepository;
import com.blocker.repository.TagRepository;
import com.blocker.request.InvestmentRequest;
import com.blocker.request.InvestmentResponse;
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
	private EditorInvestmentRepository editorinvestmentRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private BoardTagRepository boardTagRepository;
	@Autowired
	private BoardCategoryRepository boardCategoryRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@PostMapping("/create")
	@ApiOperation(value = "투자 게시글 생성")
	public Object createInvestment(@RequestBody InvestmentRequest pinvestment) {
		final BasicResponse result = new BasicResponse();
		try {
			InvestmentDto investmentdto = new InvestmentDto();
			
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

	@GetMapping("/investList")
	@ApiOperation(value = "저장되어있는 모든 투자 게시글을보여줌")
	@ResponseBody
	public Object investList(@RequestParam String userid) {
		final BasicResponse result = new BasicResponse();
		List<InvestmentDto> list = new ArrayList<>();
		List<InvestmentResponse> resultDatas = new ArrayList<>();

		list = investmentRepository.findAllByUserid(userid);
		try {
			for (Iterator<InvestmentDto> iter = list.iterator(); iter.hasNext();) {
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
	@ExceptionHandler(Exception.class)
	public void nullex(Exception e) {
		System.err.println("invest 부분에서 " + e.getClass());
		webhook w = new webhook();
		w.send("invest 부분에서 " + e.getClass());
	}
}
