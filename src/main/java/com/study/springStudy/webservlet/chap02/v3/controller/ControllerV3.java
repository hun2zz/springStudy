package com.study.springStudy.webservlet.chap02.v3.controller;


import com.study.springStudy.webservlet.ModelAndView;
import com.study.springStudy.webservlet.View;

import java.util.Map;

/*
    이 인터페이스를 구현하는 다양한 하위 객체들이
    요청 정보나 응답정보를 모두가 사용하는 것이 아니기 대문에
    요청, 응답 정보 처리를 외부로 위임시킴.
 */


public interface ControllerV3 {
    /**
     *
     * @param paramMap 요청 정보 (쿼리파라미터)를 모두 읽어서 맵에 담음
     * @return 응답시 보여줄 화면 처리 객체(View)와
     *         화면 처리를 위해 사용할 데이터 (Model)을 일괄적으로 처리하는 객체인 ModelAndView
     */
    ModelAndView process(Map<String, String> paramMap);
}
