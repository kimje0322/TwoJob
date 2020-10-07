package com.blocker.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blocker.dto.Member;
import com.blocker.dto.Property;
import com.blocker.repository.MemberRepository;
import com.blocker.request.LoginResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	Property property;

	@Autowired
	MemberRepository memberRepository;

	public Object getUserInfo(String accessToken) {
		String reqURL = "https://kapi.kakao.com/v2/user/me";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			if (responseCode == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String line = "";
				String result = "";

				while ((line = br.readLine()) != null) {
					result += line;
				}
				System.out.println("response body : " + result);

				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(result);
				JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
				JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
				String id = element.getAsJsonObject().get("id").getAsString();
				String nickname = properties.getAsJsonObject().get("nickname").getAsString();
				JsonElement nprofile_image = properties.getAsJsonObject().get("profile_image");
				JsonElement nemail = kakao_account.getAsJsonObject().get("email");
				String email = nemail == null ? null : nemail.getAsString();
				String profile_image = nprofile_image == null
						? "https://file3.instiz.net/data/cached_img/upload/2020/02/26/12/f7975c2dacddf8bf521e7e6d7e4c02ee.jpg"
						: nprofile_image.getAsString();
				Optional<Member> m = memberRepository.findById(id);
				Member m1 = null;
				LoginResponse loginResponse;
				if (m.isPresent()) {
					m1 = m.get();
					m1.setAccessToken(accessToken);
					m1.setName(nickname);
					m1.setEmail(email);
					m1.setProfileImg(profile_image);
					m1.setIsfirsttime(false);
					memberRepository.save(m1);
				} else {// 최초로그인
					m1 = new Member(id, nickname, profile_image, "KAKAO", email, accessToken);
					m1.setIsfirsttime(true);
					memberRepository.save(m1);
				}
				return m1;
			} else {
				return responseCode;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "fail";
	}

	public String Logout(String accessToken) {
		String reqURL = "https://kapi.kakao.com/v1/user/logout";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			if (responseCode == 200) {
				return "success";
			} else {
				return String.valueOf(responseCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "fail";
	}

}
