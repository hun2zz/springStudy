package com.study.springStudy.springmvc.chap05.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


//RestController는 JSON을 보내기 때문에 jsp를 여려면 ModelAndView를 사용해야함!
//그냥 Controller에서 JSON을 보내며녀 ResponseBody를 붙여줌
@Controller
@RequestMapping("/members")
@Slf4j
public class MemberController {

    //회원가입 양식 열기
    @GetMapping("/sign-up")
//    @ResponseBody // 이거 붙이면 jsp가 아니라 그냥 txt가 날라감!
    public String hello() {
        return "members/sign-up";
//        return "board/write";
    }
}
