package com.study.springStudy.springmvc.chap04.controller;

import com.study.springStudy.springmvc.chap04.comon.Page;
import com.study.springStudy.springmvc.chap04.comon.PageMaker;
import com.study.springStudy.springmvc.chap04.comon.Search;
import com.study.springStudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springStudy.springmvc.chap04.dto.BoardDto;
import com.study.springStudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springStudy.springmvc.chap04.dto.BoardWriterDto;
import com.study.springStudy.springmvc.chap04.entity.Board;
import com.study.springStudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springStudy.springmvc.chap04.service.BoardService;
import com.study.springStudy.springmvc.chap05.service.ReactionService;
import com.study.springStudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.regex.qual.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.study.springStudy.springmvc.util.LoginUtil.LOGIN;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    private final BoardService service;
    private final ReactionService reactionService;

    @GetMapping("/list")
    //1. 목록 조회 요청 url : /board/list : (GET)
    public String list(Model model, @ModelAttribute("s") Search page) {
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
    public String write1(BoardWriterDto dto, HttpSession session) {
        service.save(dto, session);
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
    @GetMapping("/detail")
    public String detail(int bno, Model model, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        System.out.println("/board/detail GET");

        // 1. 상세조회하고 싶은 글번호를 읽기
        System.out.println("bno = " + bno);

        // 2. 데이터베이스로부터 해당 글번호 데이터 조회하기
        BoardDetailResponseDto dto = service.detail(bno, request, response);

        // 3. JSP파일에 조회한 데이터 보내기
        model.addAttribute("num", dto);

        // 4. 요청 헤더를 파싱하여 이전 페이지의 주소를 얻어냄
        String ref = request.getHeader("Referer");
        model.addAttribute("ref", ref);
        return "board/detail";
    }

    //4. 좋아요 요청 비동기 처리
    @GetMapping("/like")
    @ResponseBody
    public ResponseEntity<?> like(long bno, HttpSession session) {
        log.info("like!");
        String account = LoginUtil.getLoggedUser(session);
        reactionService.like(bno, account);
        return null;
    }


    //5. 싫어요 요청 비동기 처리
    @GetMapping("/dislike")
    @ResponseBody
    public ResponseEntity<?> dislike(long bno, HttpSession session) {
        log.info("dislike!");
        String account = LoginUtil.getLoggedUser(session);
        reactionService.dislike(bno, account);
        return null;
    }
}
