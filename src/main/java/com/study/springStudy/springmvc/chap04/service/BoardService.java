package com.study.springStudy.springmvc.chap04.service;

import com.study.springStudy.springmvc.chap04.comon.Search;
import com.study.springStudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springStudy.springmvc.chap04.dto.BoardFindAllDto;
import com.study.springStudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springStudy.springmvc.chap04.entity.Board;
import com.study.springStudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springStudy.springmvc.chap05.entity.Reply;
import com.study.springStudy.springmvc.chap05.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;
    private final ReplyMapper replyMapper;

    public List<BoardListResponseDto> getList (Search page) {
        List<BoardFindAllDto> all = boardMapper.findAll(page);

        //조회해온 게시물 리스트에서 각 게시물들의 조회수를 확인하여
        // 조회수가 5이상인 게시물에 특정 마킹
        return all.stream().map(BoardListResponseDto::new).collect(Collectors.toList());
    }

    public void save(Board board) {
        boardMapper.save(board);
    }

    public BoardDetailResponseDto findOne(int num) {
        Board one = boardMapper.findOne(num);
//        List<Reply> all = replyMapper.findAll(num);
        BoardDetailResponseDto boardListResponseDto = new BoardDetailResponseDto(one);
//        boardListResponseDto.setReplies(all);
        return boardListResponseDto;
    }

    public void updateLook(BoardDetailResponseDto one) {
        boardMapper.updateLook(one);
    }

    public void delete(int num) {
        boardMapper.delete(num);
    }

    public int getCount(Search page) {
        return boardMapper.getCount(page);
    }
}
