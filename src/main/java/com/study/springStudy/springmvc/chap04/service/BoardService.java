package com.study.springStudy.springmvc.chap04.service;

import com.study.springStudy.springmvc.chap03.dto.ScoreListResponseDto;
import com.study.springStudy.springmvc.chap04.comon.Page;
import com.study.springStudy.springmvc.chap04.comon.Search;
import com.study.springStudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springStudy.springmvc.chap04.entity.Board;
import com.study.springStudy.springmvc.chap04.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;

    public List<BoardListResponseDto> getList (Search page) {
        List<Board> all = mapper.findAll(page);

        //조회해온 게시물 리스트에서 각 게시물들의 조회수를 확인하여
        // 조회수가 5이상인 게시물에 특정 마킹
        return all.stream().map(BoardListResponseDto::new).collect(Collectors.toList());
    }



    public void save(Board board) {
        mapper.save(board);
    }

    public Board findOne(int num) {
        Board one = mapper.findOne(num);
        return one;
    }

    public void updateLook(Board one) {
        mapper.updateLook(one);
    }

    public void delete(int num) {
        mapper.delete(num);
    }

    public int getCount(Search page) {
        return mapper.getCount(page);
    }
}
