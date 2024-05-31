package com.study.springStudy.springmvc.interceptor;


import com.study.springStudy.springmvc.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@Slf4j
public class BoardInterceptor implements HandlerInterceptor {

    //preHandle을 구현하여
    //로그인을 안한 회원은 글쓰기, 글수정, 글삭제 요청을 거부
    //거부하고 로그인 페이지로 리다이렉션

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("after login interceptor execute!");
        if (!LoginUtil.isLoggedIn(request.getSession())) {
            String requestURI = request.getRequestURI();
            response.sendRedirect("/members/sign-in?message=login-required&redirect=" + requestURI);

            return false; // true 일 경우 컨트롤러 진입 허용, false 진입 차단

//            String ref = request.getHeader("Referer");
//            model.addAttribute("ref", ref);
        }
        return true;
    }
}
