package com.study.springStudy.springmvc.chap05.rest;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/rest")
public class RestController {

    @GetMapping("/hello")
    @ResponseBody // 서버가 클라이언트에게 순수한 데이터를 전달할 때 사용
    public String hello() {
        return "안녕안녕 메롱^^";
    }

    @GetMapping("/hobby")
    @ResponseBody
    public List<String> hobby() {
        List<String> strings = List.of("태권도,", "장기", "댄스");
        return strings;
    }
}
