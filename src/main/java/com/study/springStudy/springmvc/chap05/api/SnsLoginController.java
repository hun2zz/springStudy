package com.study.springStudy.springmvc.chap05.api;


import com.study.springStudy.springmvc.chap05.service.SnsLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SnsLoginController {
    private final SnsLoginService snsLoginService;

    @Value("${sns.kakao.app-key}")
    private String appKey;
    @Value("${sns.kakao.redirect-uri}")
    private String redirectUri;
    @GetMapping("/kakao/login")
    public String kakaoLogin() {

        //1. 카카오 서버로 인가코드 발급 통신을 해야 함.
        String uri = "https://kauth.kakao.com/oauth/authorize";
        //2. ? 뒤에 필수 쿼리파라미터를 넣어서 보내줘야함.
        uri += "?client_id=" + appKey;
        uri += "&redirect_uri=" + redirectUri;
        uri += "&response_type=code";
        return "redirect:" + uri; // 3. get 방식으로 리다이렉트임. 카카오 서버로~
    }



    //4. 인가 코드를 받는 요청 메서드
    @GetMapping("/oauth/kakao")
    public String kakaoCodee(String code) {
        log.info("카카오 인가코드 발급 - {}", code);
        //토큰 발급에 필요한 파라미터 만들기
        HashMap<Object, Object> requestParams = new HashMap<>();
        requestParams.put("appKey", appKey);
        requestParams.put("redirect", redirectUri);
        requestParams.put("code", code);
        //5. 인증 액세스 토큰 발급 요청 - > 서비스로 위임함.
        snsLoginService.kakaoLogin(requestParams);
        return  "";
    }
}
