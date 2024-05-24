package com.study.springStudy.springmvc.chap05.rest;


import com.study.springStudy.database.chap01.Person;
import com.study.springStudy.springmvc.chap04.comon.Page;
import com.study.springStudy.springmvc.chap04.comon.PageMaker;
import com.study.springStudy.springmvc.chap04.comon.Search;
import com.study.springStudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springStudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springStudy.springmvc.chap04.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//@Controller
//@ResponseBody
@RequiredArgsConstructor
@RestController // Controller 와 ResponseBody가 합쳐져있음.
@RequestMapping("/rest")
public class RestApiController {

    private final BoardService boardService;
    @GetMapping("/hello")
    public String hello() {
        return "안녕안녕 메롱^^";
    }

    @GetMapping("/hobby")
    public List<String> hobby() {
        List<String> strings = List.of("태권도,", "장기", "댄스");
        return strings;
    }

    @GetMapping(value = "/person", produces = "application/json")
    public Person person() {
        Person person = new Person(100, "진멍멍", 10);
        return person;
    }

    @GetMapping("/board")
    public Map<String, Object> board () {
        List<BoardListResponseDto> list = boardService.getList(new Search());
        Map<String, Object> map = new HashMap<>();
        map.put("articles", list);
        map.put("page", new PageMaker(new Page(), boardService.getCount(new Search())));
        return map;
    }


    /*
         RestController에서 리턴타입을 ResponseEntity를 쓰는 이유

         - 클라이언트에게 응답할 때는 메시지 바디안에 들어 있는 데이터도 중요하지만
         - 상태코드와 헤더정보를 포함해야 한다
         - 근데 일반 리턴타입은 상태코드와 헤더정보를 전송하기 어렵다
     */

    @GetMapping("/people")
    public ResponseEntity<?> people() {
        Person p1 = new Person(111, "진땡땡", 30);
        Person p2 = new Person(112, "도라미", 10);
        Person p3 = new Person(113, "키득", 20);
        List<Person> p11 = List.of(p1, p2, p3);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("mypet","dog");
        httpHeaders.add("mymoney","no..");
//        200번은 잘됐을때,
//            400번은 클라이언트에서 잠롯 입력,
//        403은 유료 페이지에 무료 유저가 들어왔을때,
//        500은 서버가 잘못했을대


        return ResponseEntity.status(200).headers(httpHeaders).body(p11);
    }

    @GetMapping("/bmi")
    public ResponseEntity<?> bmi(@RequestParam(required = false) Double cm,@RequestParam(required = false) Double kg) {
        if ( cm == null || kg == null) {
            return ResponseEntity.badRequest().body("키와 몸무게를 전달해주세요 ^^.");
        }
        double m = cm / 100;
        double bmi = kg / (m*m) ;
        return ResponseEntity.status(200).body(bmi);

    }

}
