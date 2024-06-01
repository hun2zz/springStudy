package com.study.springStudy.springmvc.interceptor;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.study.springStudy.springmvc.chap04.entity.Board;
import com.study.springStudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springStudy.springmvc.util.LoginUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BoardInterceptor implements HandlerInterceptor {
    private final BoardMapper boardMapper;
    //preHandle을 구현하여
    //로그인을 안한 회원은 글쓰기, 글수정, 글삭제 요청을 거부
    //거부하고 로그인 페이지로 리다이렉션

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("after login interceptor execute!");
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/board/delete")) {
            int bno = Integer.parseInt(request.getParameter("num"));
            Board one = boardMapper.findOne(bno);
            if (one.getAccount().equals(LoginUtil.getLoggedUser(request.getSession()))){
                return true;
            } else if (!one.getAccount().equals(LoginUtil.getLoggedUser(request.getSession()))){
                response.sendRedirect("/members/sign-in?message=login-required&redirect=/board/list" );
                return false;
            }
            if (!LoginUtil.isLoggedIn(request.getSession())) {
                response.sendRedirect("/members/sign-in?message=login-required&redirect=/board/list");
                return false; // true 일 경우 컨트롤러 진입 허용, false 진입 차단

//            String ref = request.getHeader("Referer");
//            model.addAttribute("ref", ref);

                //삭제 요청이 들어오면 서버에서 한번 더 관리자인지 ? 자기가 쓴글인지 체크하기
            }
            return true;
        }
        return true;
    }
}
//}
