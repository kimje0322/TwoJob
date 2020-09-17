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
import org.springframework.web.bind.annotation.RestController;

import com.blocker.dto.Member;
import com.blocker.dto.Userinfo;
import com.blocker.repository.MemberRepository;
import com.blocker.repository.UserInfoRepository;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping
public class LoginContoroller {
    @Autowired
    MemberRepository MemberRepository;
    @Autowired
    UserInfoRepository userInfoRepository;
    
    // 4. 로그아웃 기능
    @ApiOperation(value = "[logout 기능] 로그아웃을 진행한다.")
    @PostMapping(path="/newuser/out")
    public void logout() {
    }
    
    @GetMapping(path="/newuser/Login")
    public Member getMyAuthenticationFromSession(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String id = oAuth2User.getAttribute("id");
        Member m = MemberRepository.findByOauthId(id);
        return m;
    }
    @PostMapping("account/kakaologin")
    public Map<String, Object> kakaologin(@RequestBody Userinfo userInfo) {
    	Optional<Userinfo> user = userInfoRepository.findByEmail(userInfo.getEmail());
    	Map<String, Object> m = new HashMap<>(); 
    	if(user.isPresent()) {
    		m.put("state", "success");
    		m.put("userinfo", user.get());
    		return m;
    	}else {
    		userInfoRepository.save(userInfo);
    		m.put("state", "false");
    		return m;
    	}
     }
    
}
