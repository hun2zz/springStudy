package com.study.springStudy.springmvc.chap05.service;


import com.study.springStudy.springmvc.chap05.dto.request.SignUpDto;
import com.study.springStudy.springmvc.chap05.dto.response.AccessTokenDto;
import com.study.springStudy.springmvc.chap05.dto.response.KaKaoUserDto;
import com.study.springStudy.springmvc.chap05.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SnsLoginService {
    private final MemberService memberService;
    private final MemberMapper memberMapper;
    @Value("${sns.kakao.app-key}")
    private String appKey;
    @Value("${sns.kakao.redirect-uri}")
    private String redirectUri;
    //카카오 로그인 처리 서비스 로직
    public void kakaoLogin(Map<Object, Object> requestParams, HttpSession  session) {
        //토큰 발급 요청
        String accessToken = getKakaoAccessToken(requestParams);
        
        //10 발급 받은 토큰으로 사용자 정보 가져오기
        KaKaoUserDto.Properties userInfo = getKakaoUserInfo(accessToken);

        //11. 카카오에서 받은 회원정보로 우리 사이트 회원가입 시키기
        //회원 중복확인
        String account = userInfo.getNickname();
        if(memberService.checkIdentifier("account",account)) {
            throw new IllegalArgumentException("이미 가입한 회원입니다.");
        }
        memberService.join(
                SignUpDto.builder()
                        //카카오에서 계정을 못 가져왔기 때문에 임시로 닉네임, 패스워드를 함.
                        .account(account).password("0000").name(account).email(account + "@abc.com").build(),
                userInfo.getProfileImage()
        );

        //12. 우리 사이트 로그인 처리
        MemberService.maintainLoginState(session, memberMapper.findOne(account));
        //세션에 현재 로그인 방식에 대해 지정
        session.setAttribute("loginPath", "kakao");


    }
    //토큰으로 사용자 정보 요청
    private KaKaoUserDto.Properties getKakaoUserInfo(String accessToken) {
        String requestUri = "https://kapi.kakao.com/v2/user/me";
        //헤더설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        //11. 요청 보내기
        RestTemplate template = new RestTemplate();
        ResponseEntity<KaKaoUserDto> response = template.exchange(
                requestUri, HttpMethod.POST, new HttpEntity<>(headers), KaKaoUserDto.class
        );

        //12. 응답 정보 JSON 꺼내기
        KaKaoUserDto json = response.getBody();
        log.debug("user profile - {}" , json);
        return json.getProperties();
    }

    
    //인가코드로 토큰 발급 요청
    
    private String getKakaoAccessToken(Map<Object, Object> requestParams) {
        //6. 요청 URI
        String requestUri = "https://kauth.kakao.com/oauth/token"; // < 카카오가 정한 토큰 Uri 임.

        // 7. 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 8. POST 요청은 쿼리 파라미터를 URI에 붙일 수 없음.
        //그렇기 때문에 요청 바디에 쿼리 파라미터를 넣어야 함.
        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", requestParams.get("appKey"));
        params.add("redirect_uri", requestParams.get("redirect"));
        params.add("code", requestParams.get("code"));
        //10 . 요청 헤더와 요청 바디를 담을 객체 생성
        HttpEntity<Object> entity = new HttpEntity<>(params, headers);

        //9 카카오 서버로 POST 요청 보내기
        RestTemplate template = new RestTemplate();
        /**
         * - RestTemplate객체가 REST API 통신을 위한 API인데 (자바스크립트 fetch역할)
         *             - 서버에 통신을 보내면서 응답을 받을 수 있는 메서드가 exchange
         *
         *             param1: 요청 URL
         *             param2: 요청 방식 (get, post, put, patch, delete…)
         *             param3: 요청 헤더와 요청 바디 정보 - HttpEntity로 포장해서 줘야 함
         *             param4: 응답결과(JSON)를 어떤 타입으로 받아낼 것인지 (ex: DTO로 받을건지 Map으로 받을건지)
         */
        ResponseEntity<AccessTokenDto> response = template.exchange(requestUri, HttpMethod.POST, entity, AccessTokenDto.class);// exchange가 페치랑 같은거임.

//        log.debug("response : {} ",response);
        AccessTokenDto json = response.getBody();
        log.debug("json : {}", json);
        String accessToken = json.getAccessToken();
        log.debug("accessToken - {}", accessToken);
        log.debug("refreshToken - {}", json.getRefreshToken());

//페치는 서버에서 클라이언트로 통신하는거고 exchange는 서버랑 서버끼리 통신할 때 사용함.
        return accessToken;
    }
}
