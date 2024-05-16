package com.study.springStudy.springmvc.chap01;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("spring/chap01/*") // < 스프링 프론트 컨트롤러에 추가시킨다는 뜻.
public class BasicController {
    //디테일한 요청을 메서드로 씀
    @RequestMapping("/hello")

    // url에 /spring/chap01/hello 라는 주소가 들어오면 hello() 메서드를 실행한다는 뜻임.

    //리턴은 무조건 String임  메서드 이름은 내 맘.
    public String hello() {
        System.out.println("hello 요청이 들어옴!");
        //리턴에 실행할 jsp를 넣어주면 자동으로 jsp 접두사 ,접미사를 추가해서 포워딩 시켜줌.
        return "hello";
    }

    // ==== 요청 파라미터 읽기 ===== // < 이것을 용어적으로 Query String 이라고 부른다.
    // URL 붙어있거나 form 태그에서 전송된 데이터를 쿼리 스트링이라고 부름.

    //1. HttpServletRequest를 사용하는 방법.
    @RequestMapping("/person")
    public String person(HttpServletRequest request) {
        System.out.println("/person !! ");
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        System.out.println("age = " + age);
        System.out.println("name = " + name);

        return "";
    }

    // 2. @RequestParam 사용하기
    // /spring/chap01/major?stu=kim&major=business&grade=3 이라고 날라온다면.
    @RequestMapping("/major")
    public String major(@RequestParam String stu,@RequestParam String major,@RequestParam int grade) {
        System.out.println("stu = " + stu);
        System.out.println("major = " + major);
        System.out.println("grade = " + grade);
        return "";
    }

    // 3. 커맨드 객체(RequestDTO) 사용하기
    //ex /spring/chap01/order?orderNum=22&goods=구두&amount=3&price=20000... 라는 요청이 들어왔을떄 파라미터가 너무 많음.
    @RequestMapping("/order")
    public String order(OrderDto order) {
        System.out.println("주문번호 = " + order.getOrderNum());
        System.out.println("굿즈 = " + order.getGoods());
        System.out.println("수량 = " + order.getAmount());
        System.out.println("가격 = " + order.getPrice());
        return "";
    }

}
