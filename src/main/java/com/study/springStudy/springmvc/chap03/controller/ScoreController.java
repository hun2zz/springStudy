package com.study.springStudy.springmvc.chap03.controller;


import com.study.springStudy.springmvc.chap03.dto.ScoreDetailResponseDto;
import com.study.springStudy.springmvc.chap03.dto.ScoreListResponseDto;
import com.study.springStudy.springmvc.chap03.dto.ScoreModifyRequestDto;
import com.study.springStudy.springmvc.chap03.dto.ScorePostDto;
import com.study.springStudy.springmvc.chap03.entity.Score;
import com.study.springStudy.springmvc.chap03.repository.ScoreJdbcRepository;
import com.study.springStudy.springmvc.chap03.repository.ScoreRepository;
import com.study.springStudy.springmvc.chap03.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
    # 요청 URL
    1. 학생 성적정보 등록화면을 보여주고 및 성적정보 목록조회 처리
    - /score/list : GET

    2. 성적 정보 등록 처리 요청
    - /score/register : POST

    3. 성적정보 삭제 요청
    - /score/remove : POST

    4. 성적정보 상세 조회 요청
    - /score/detail : GET
 */
@Controller
@RequestMapping("/score")
@RequiredArgsConstructor
public class ScoreController {

    // 데이터베이스 처리를 해주는 의존객체 설정하기.
    private final ScoreService service;
//    @Autowired // -- 롬복에서 파이널 생성자를 만들어주기 때문에 이 코드를 작성하지 않아도 괜찬흥ㅁ.
//    public ScoreController(ScoreRepository repository) {
//        this.repository = repository;
//    }

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "num") String sort, Model model) {
        System.out.println("/score/list : GET!");
//        List<Score> scoreL = repository.findAll(sort);
        List<ScoreListResponseDto> dtos = service.getList(sort);


        model.addAttribute("sList", dtos);
        return "score/score-list";
    }

    @PostMapping("/register")
    public String register(ScorePostDto dto) {
        System.out.println("/score/register : POST!");
        System.out.println(dto);
        Score score = new Score(dto);

        //데이터 베이스에 저장
        service.insert(dto);

        //다시 조회로 돌아가야 저장된 데이터를 볼 수 있음.
        return "redirect:/score/list";
    }
    @GetMapping("/remove")
    public String remove(long sn) {
        service.deleteScore(sn);
        return "redirect:/score/list";
    }


    @GetMapping("/detail")
    public String detail(long stuNum, Model model) {
        ScoreJdbcRepository repository = new ScoreJdbcRepository();
        System.out.println("/score/retail : GET!");
//        System.out.println("stuNum = " + stuNum);
        //1. 상세 조회를 원하는 학번을 읽기
        //2. db에 상세조회 요청하기
        ScoreDetailResponseDto retrieve = service.retrieve(stuNum);
        model.addAttribute("s", retrieve);
        //3. db에서 조회한 회원정보 jsp 에게 전달

        return "score/score-detail";
    }


    //수정 화면 열기 요청
    @GetMapping("/modify")
    public String modify(long stuNum, Model model) {
        ScoreDetailResponseDto retrieve = service.retrieve(stuNum);
        model.addAttribute("s", retrieve);
        return "score/score-modify";
    }


    //수정 데이터 반영 요청
    @PostMapping("/modify")
    public String modify(ScoreModifyRequestDto dto)  {
        //1. 수정을 원하는 새로운 데이터 읽기 ( 국영수점수 + 학번)
        System.out.println("dto = " + dto);
        //2. 서비스에게 수정 위임
        service.update(dto);
        return "redirect:/score/detail?stuNum=" + dto.getStuNum();

    }



}
