package com.blocker.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blocker.dto.Account;
import com.blocker.dto.Token;
import com.blocker.jwt.JwtTokenUtil;
import com.blocker.repository.AccountRepository;
import com.blocker.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping
public class LoginContoroller {
    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
	
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager am;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    
    
    // 1-3. 회원가입 진행
    @ApiOperation(value = "[회원가입 기능] 신규회원을 등록한다.")
    @PostMapping(path="/newuser/add") // Map ONLY POST Requests
    public Map<String, Object> addNewUser (@RequestBody Account account) {
        String un = account.getUsername();
        Map<String, Object> map = new HashMap<>();
        
        
        // 아이디: account.getUsername()
        // 닉네임: account.getNickname()
        System.out.println("[회원가입] 요청 아이디: "+un + " 비밀번호: " + account.getPassword());
        
        // 아이디 중복체크 진행
        if (accountRepository.findByUsername(un) == null) {
            account.setUsername(un);
            account.setEmail(account.getEmail());
            
            // username이 admin 이라면 admin role 주기 (우리가 바꿔도 될듯) 쌀로그로 할까?
            if (un.equals("admin")) {
                account.setRole("ROLE_ADMIN");
            } else {
                account.setRole("ROLE_USER");
            }
            account.setPassword(bcryptEncoder.encode(account.getPassword()));          
            map.put("success", true); // 등록성공
            accountRepository.save(account); // db에 저장
            return map;
        } else {
            map.put("success", false);
            map.put("message", "duplicated username"); // 에러 메시지 리턴
        }
        return map;
    }
    
    
    // 2. 로그인 요청
    @ApiOperation(value = "[로그인 기능] 회원로그인을 진행한다.")
    @PostMapping(path = "/newuser/login")
    public Map<String, Object> login(@RequestBody Map<String, String> m) throws Exception {
        final String username = m.get("username");
        final String password = m.get("password");
        
        // 디버깅용
        Iterator<String> mapIter = m.keySet().iterator();
        while(mapIter.hasNext()){
            String key = mapIter.next();
            String value = m.get( key );
            System.out.println(key+" : "+value);
        }
        
        try {
        	// 현재 사용자가 입력한 username과 password가 내 DB의 정보와 일치하는가?
            am.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e){
        	System.out.println("비정상적인 요청입니다.");
            throw e;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(username);

        Token retok = new Token();
        retok.setUsername(username);
        retok.setRefreshToken(refreshToken);

        //발행한 redis에 저장하는 로직으로, hashmap과 같은 key,value 구조임
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set(username, retok); // key, value 값으로 redis에 저장

        logger.info("generated access token: " + accessToken);
        logger.info("generated refresh token: " + refreshToken);
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return map;
    }
    
    @ApiOperation(value = "[Token refresh 기능] 신규 Token 요청을 진행한다.")
    @PostMapping(path="/newuser/refresh")
    public ResponseEntity<Map<String, Object>>  requestForNewAccessToken(HttpServletResponse response) {
    	String refreshTokenFromDb = null;
        Map<String, Object> map = new HashMap<>();
        String refreshToken =response.getHeader("jwtToken2");
        try {
            if (refreshToken != null) { //refresh를 같이 보냈으면.
            	String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
                try {
                    ValueOperations<String, Object> vop = redisTemplate.opsForValue();
                    Token result = (Token) vop.get(username);
                    refreshTokenFromDb = result.getRefreshToken();
                    logger.info("rtfrom db: " + refreshTokenFromDb);
                } catch (IllegalArgumentException e) {
                    logger.warn("illegal argument!!");
                }
                System.out.println();
                //둘이 일치하고 만료도 안됐으면 재발급 해주기.
                if (refreshToken.equals(refreshTokenFromDb) && !jwtTokenUtil.isTokenExpired(refreshToken)) {
                    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    String newtok =  jwtTokenUtil.generateAccessToken(userDetails);
                    map.put("success", true);
                    map.put("accessToken", newtok);
                    System.out.println("accessToken = " + newtok);
                } else {
                    map.put("success", false);
                    map.put("msg", "refresh token is expired.");
                }
            } else { //refresh token이 없으면
                map.put("success", false);
                map.put("msg", "your refresh token does not exist.");
            }

        } catch (Exception e) {
            throw e;
        }
        return new ResponseEntity<Map<String, Object>>(map,HttpStatus.OK);
    }
    
    // 4. 로그아웃 기능
    @ApiOperation(value = "[logout 기능] 로그아웃을 진행한다.")
    @PostMapping(path="/newuser/out")
    public ResponseEntity<?> logout(HttpServletResponse response) {
    	String username = response.getHeader("username");
        // String accessToken = response.getHeader("jwtToken");
         try {
             if (redisTemplate.opsForValue().get(username) != null) {
                 redisTemplate.delete(username); // refresh token 삭제
             }
         } catch (IllegalArgumentException e) {
             logger.warn("[ERROR] 사용자가 존재하지 않습니다!");
         }
         return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
