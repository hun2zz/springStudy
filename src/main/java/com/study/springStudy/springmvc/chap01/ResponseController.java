package com.study.springStudy.springmvc.chap01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ResponseController {
    // JSP 파일로 포우딩할 때 데이터 전달하기
    // 1. Model 객체 사용하기
    @RequestMapping("/hobbies")
    public String hobbies(Model model) {
        model.addAttribute("name", "김철수");
        // name 이라는 키값에 김철수, hobbies라는 키값에 볼링축구게임 리스트를 mvc/hobbies에 jsp파일로 보냄.
        model.addAttribute("hobbies", List.of("볼링","축구","게임"));
        model.addAttribute("major", "경영학");
        return "mvc/hobbies";
    }


    //2 . ModelAndView 객체 사용하기!
    @RequestMapping("/hobbies2")
    public ModelAndView hobbies2() {
        ModelAndView mv = new ModelAndView("mvc/hobbies");
        mv.addObject("name","박영희");
        mv.addObject("hobbies",List.of("멍때리기","볼링"));
        mv.addObject("major","컴퓨터 과학");
        return mv;
    }
}
