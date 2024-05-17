package com.study.springStudy.springmvc.chap04.controller;

import com.study.springStudy.springmvc.chap04.dto.BoardDto;
import com.study.springStudy.springmvc.chap04.entity.Board;
import com.study.springStudy.springmvc.chap04.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;

    @GetMapping("/list")
    //1. 목록 조회 요청 url : /board/list : (GET)
    public String list (String board, Model model) {
        List<Board> scoreL = boardRepository.findAll(board);
        model.addAttribute("sList", scoreL);
        return "board/list";
    }


    //2. 글쓰기 양식 화면 열기 요청 url : /board/write : (GET)
    @GetMapping("write")
    public String write(String writeCopy) {
        return "board/write";
    }

    //3. 게시글 등록 요청 url : /board/write : (POST)
    //-> 등록이 끝나면 목록조회 요청을 리다이렉션 해야함.
    @PostMapping("write")
    public String write1(BoardDto dto) {

        Board board = new Board(dto);
        boardRepository.save(board);
        return "redirect:/board/list";
    }


    //4. 게시글 삭제 요청 /board/delete : (GET)
    //-> 삭제 완료하고 조회요청 리다이렉션.
    @GetMapping("delete")
    public String delete(int num) {
        boardRepository.delete(num);
        return "redirect:/board/list";
    }


    //5. 게시글 상세 조회 요청 url : /board/detail (GET)
    @GetMapping("detail")
    public String detail(int num, Model model){
        Board one = boardRepository.findOne(num);
        boardRepository.updateLook(one, num);
        model.addAttribute("num", one);

        return "board/detail";
    }
}
