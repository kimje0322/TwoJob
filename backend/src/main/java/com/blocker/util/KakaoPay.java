package com.blocker.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.blocker.dto.KakaoPayApprovalDto;
import com.blocker.dto.KakaoPayReadyDto;

import lombok.extern.java.Log;

@Service
@Log
public class KakaoPay {

	private KakaoPayReadyDto kakaoPayReadyDto;
	private KakaoPayApprovalDto kakaoPayApprovalDto;

	private static final String host = "https://kapi.kakao.com";

	public String kakaoPayReady(int count) {
		RestTemplate restTemplate = new RestTemplate();

		// 서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + "f7fdfa544b08faac2ca0d9d3e9885db4");
		headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

		// 서버로 요청할 Body
		// (내가 결제로 지정할 데이터들을 넣는다 하지만 kakao Developers에 필수라고 써있는 파라미터들은 꼭 넣어 준다)
		// 현제 추가한 데이터들은 모두 필수 데이터이다
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("cid", "TC0ONETIME"); // 가맹점코드 테스트할때는 TC0ONETIME을 넣는다
		params.add("partner_order_id", "1001"); // 가맹점 주문번호
		params.add("partner_user_id", "tojob");// 가맹점 회원 id
		params.add("item_name", "ether"); // 상품명
		params.add("quantity", count + ""); // 수량
		params.add("total_amount", (count * 10) + ""); // 상품 총액
		System.out.println("count=======>" + (count * 10));
		params.add("tax_free_amount", "0"); // 상품 비과세 금액
		// 프론트 주소나오면 다시 작성해야함
		params.add("approval_url", "http://j3b102.p.ssafy.io:8080/kakaopay/kakaoPayReadySuccess?totalprice="+(count*10)); // 결제 성공시 redirect할
																									// url
		params.add("cancel_url", "http://j3b102.p.ssafy.io:8080"); // 결제 취소시 redirect할 url (결제 페이지로 바꿀 예정)
		params.add("fail_url", "http://j3b102.p.ssafy.io:8080"); // 결제 실패시 redirect할 url (결제 페이지로 바꿀 예정)

		// 헤더와 바디를 붙이는 방법
		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

		try {
			// RestTemplate을 이용해 카카오페이에 데이터를 보내는 방법.
			// post방식으로 HOST + "/v1/payment/ready"에 body(header+body)정보를 보낸다
			// 정보를 보내고 요청이 성공적으로 이루어지면 카카오페이에서 응답정보를 보내준다.
			// kakaoPayReadyDto.class는 응답을 받는 객체를 설정한 것이다.
			kakaoPayReadyDto = restTemplate.postForObject(new URI(host + "/v1/payment/ready"), body,
					KakaoPayReadyDto.class);
			System.out.println(kakaoPayReadyDto.getTid());
			System.out.println(kakaoPayReadyDto.getNext_redirect_pc_url());
			System.out.println(kakaoPayReadyDto.getCreated_at());
			return kakaoPayReadyDto.getNext_redirect_pc_url();

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return "/pay";
	}

	public KakaoPayApprovalDto kakaoPayInfo(String pg_token, String totalprice) {

		RestTemplate restTemplate = new RestTemplate();

		// 서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + "f7fdfa544b08faac2ca0d9d3e9885db4");
		headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

		// 서버로 요청할 Body
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("cid", "TC0ONETIME");
		params.add("tid", kakaoPayReadyDto.getTid());
		params.add("partner_order_id", "1001");
		params.add("partner_user_id", "tojob");
		params.add("pg_token", pg_token);
		params.add("total_amount", totalprice);

		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

		try {
			kakaoPayApprovalDto = restTemplate.postForObject(new URI(host + "/v1/payment/approve"), body,
					KakaoPayApprovalDto.class);

			return kakaoPayApprovalDto;

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return null;
	}

}
