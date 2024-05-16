package com.study.springStudy.springmvc.chap03.controller;


import com.study.springStudy.springmvc.chap03.dto.ScorePostDto;
import com.study.springStudy.springmvc.chap03.entity.Score;
import com.study.springStudy.springmvc.chap03.repository.ScoreJdbcRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
public class ScoreController {

    // 데이터베이스 처리를 해주는 의존객체 설정하기.
    private ScoreJdbcRepository repository = new ScoreJdbcRepository();

    @GetMapping("/list")
    public String list(Model model) {
        System.out.println("/score/list : GET!");
        List<Score> scoreL = repository.findAll();
        model.addAttribute("sList", scoreL);
        return "score/score-list";
    }
    @PostMapping("/register")
    public String register(ScorePostDto dto) {
        System.out.println("/score/register : POST!");
        System.out.println(dto);
        Score score = new Score(dto);

        //데이터 베이스에 저장
        repository.save(score);

        //다시 조회로 돌아가야 저장된 데이터를 볼 수 있음.
        return "redirect:/score/list";
    }
    @PostMapping("/remove")
    public String remove() {
        System.out.println("/score/remove : POST!");
        return "";
    }
    @GetMapping("/detail")
    public String detail() {
        System.out.println("/score/retail : GET!");
        return "";
    }


}
