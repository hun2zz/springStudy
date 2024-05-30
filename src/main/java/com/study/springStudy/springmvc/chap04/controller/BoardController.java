package com.study.springStudy.springmvc.chap04.controller;

import com.study.springStudy.springmvc.chap04.comon.Page;
import com.study.springStudy.springmvc.chap04.comon.PageMaker;
import com.study.springStudy.springmvc.chap04.comon.Search;
import com.study.springStudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springStudy.springmvc.chap04.dto.BoardDto;
import com.study.springStudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springStudy.springmvc.chap04.entity.Board;
import com.study.springStudy.springmvc.chap04.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.regex.qual.Regex;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService service;

    @GetMapping("/list")
    //1. 목록 조회 요청 url : /board/list : (GET)
    public String list (Model model, @ModelAttribute ("s") Search page) {
        List<BoardListResponseDto> scoreL = service.getList(page);
        // 페이지 정보를 생성하여 JSP에게 전송
        PageMaker pageMaker = new PageMaker(page, service.getCount(page));

        //클라이언트에 데이터를 보내기전에 렌더링에 필요한 데이터만 추출하기
        model.addAttribute("sList", scoreL);
        model.addAttribute("maker", pageMaker);

        return "board/list";
    }


    //2. 글쓰기 양식 화면 열기 요청 url : /board/write : (GET)
    @GetMapping("write")
    public String write(String writeCopy) {
        return "board/write";
    }


//    3. 게시글 등록 요청 url : /board/write : (POST)
//    -> 등록이 끝나면 목록조회 요청을 리다이렉션 해야함.
    @PostMapping("write")
    public String write1(BoardDto dto) {
        Board board = new Board(dto);
        service.save(board);
        return "redirect:/board/list";
    }


    //4. 게시글 삭제 요청 /board/delete : (GET)
    //-> 삭제 완료하고 조회요청 리다이렉션.
    @GetMapping("delete")
    public String delete(int num) {
        service.delete(num);
        return "redirect:/board/list";
    }


    //5. 게시글 상세 조회 요청 url : /board/detail (GET)
    @GetMapping("detail")
    public String detail(int bno, Model model, HttpServletRequest request){
        BoardDetailResponseDto one = service.findOne(bno);
        service.updateLook(one);



        model.addAttribute("num", one);

        //요청 헤더를 파싱하여 이전 페이지의 주소를 ㅓ얻어낸다.
        String ref = request.getHeader("Referer");
        model.addAttribute("ref", ref);

        return "board/detail";
    }
}
