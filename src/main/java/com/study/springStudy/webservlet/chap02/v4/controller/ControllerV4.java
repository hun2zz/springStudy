package com.study.springStudy.webservlet.chap02.v4.controller;

import com.study.springStudy.webservlet.Model;
import com.study.springStudy.webservlet.ModelAndView;

import java.util.Map;

public interface ControllerV4 {

    /**
    *
     * @param paramMap 요청 정보 (쿼리파라미터)를 모두 읽어서 맵에 담음
     * @param2 model : 응답시 보여줄 JSP에 보낼 데이터를 담는 수송객체
     * @return 응답시 포워딩하거나 리다이렉트할 경로 문자열
     */
    String process(Map<String, String> paramMap, Model model);
}
