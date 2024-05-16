package com.study.springStudy.springmvc.chap02;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/coffee/*")
public class CoffeeController {
    /**
     * @request-uri : /coffee/order
     * @forwawrding-jsp : /WEB-INF/views/mvc/coffee-form.jsp
     */

    //GET 요청만 받겠다.
    @GetMapping("/order")
    public String order() {

        return "mvc/coffee-form";
    }


    //게시판 같은 경우 url에 포함되어 있어야 친구에게 공유가 가능함.
    //POST 요청만 받겠다. RequestMapping은 get,post 전부 받음.
    @PostMapping("/result")
    public String result(String menu, int price, Model model) {

        //1. 주문 데이터 (menu, price) 읽어오기

        //2. jsp에 보내서 렌더링시키기.

        model.addAttribute("mmm", menu);
        model.addAttribute("ppp", price);
        return "mvc/coffee-result";
    }
}
