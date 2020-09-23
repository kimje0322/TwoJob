package com.blocker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.dto.EditorInvestmentDto;
import com.blocker.dto.InvestmentDto;
import com.blocker.repository.EditorInvestmentRepository;
import com.blocker.repository.InvestmentRepository;
import com.blocker.request.InvestmentRequest;
import com.blocker.util.BasicResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/investment")
public class InvestController {

	@Autowired
	private InvestmentRepository investmentRepository;
	@Autowired
	private EditorInvestmentRepository editorinvestmentRepository;

	@PostMapping("/create")
	@ApiOperation(value = "투자 게시글 생성")
	public Object createInvestment(@RequestBody InvestmentRequest pinvestment) {
		final BasicResponse result = new BasicResponse();

		InvestmentDto investmentdto = new InvestmentDto();
		investmentdto.setCompname(pinvestment.getCompName());
		investmentdto.setDeadline(pinvestment.getDeadLine());
		investmentdto.setExpectedsaleprice(pinvestment.getExpectedSalePrice());
		investmentdto.setGoalprice(pinvestment.getGoalPrice());
		investmentdto.setIdentity(pinvestment.getIdentity());
		investmentdto.setIntroduce(pinvestment.getIntroduce());
		investmentdto.setOnelineintro(pinvestment.getOneLineIntro());
		investmentdto.setPictual(pinvestment.getPictual());
		investmentdto.setPjtname(pinvestment.getPjtName()); 
		investmentdto.setUrl(pinvestment.getUrl());

		InvestmentDto tempInvestmentDto = investmentRepository.save(investmentdto);

		//editor값 넣기
		EditorInvestmentDto editorInvestmentDto = new EditorInvestmentDto();
		editorInvestmentDto.setEditorhtml(pinvestment.getEditorhtml());
		editorInvestmentDto.setInvestnum(tempInvestmentDto.getNum());
		editorinvestmentRepository.save(editorInvestmentDto);
		
		result.data = "Success";
		result.status = true;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
