package com.study.springStudy.springmvc.chap05.service;


import com.study.springStudy.springmvc.chap04.comon.Page;
import com.study.springStudy.springmvc.chap04.comon.PageMaker;
import com.study.springStudy.springmvc.chap05.dto.request.ReplyPostDto;
import com.study.springStudy.springmvc.chap05.dto.request.ReplyUpdateDto;
import com.study.springStudy.springmvc.chap05.dto.response.ReplyDetailDto;
import com.study.springStudy.springmvc.chap05.dto.response.ReplyListDto;
import com.study.springStudy.springmvc.chap05.entity.Reply;
import com.study.springStudy.springmvc.chap05.mapper.ReplyMapper;
import com.study.springStudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyService {
    public final ReplyMapper replyMapper;

    //댓글 목록 전체 조회
    public ReplyListDto getReplies(long boardNo, Page page) {
        List<Reply> all = replyMapper.findAll(boardNo, page);
        List<ReplyDetailDto> dtolist = all.stream().map(r -> new ReplyDetailDto(r)).collect(Collectors.toList());
        return ReplyListDto.builder().replies(dtolist).pageInfo(new PageMaker(page, replyMapper.count(boardNo))).build();
    }

    //댓글 입력
    public boolean register(ReplyPostDto dto, HttpSession session) {
        Reply reply = Reply.builder().replyText(dto.getText()).replyWriter(dto.getAuthor()).boardNo(dto.getBno()).build();
        reply.setAccount(LoginUtil.getLoggedUser(session));
        boolean flag = replyMapper.save(reply);
        if (flag ) log.info("댓글 등록 성공 !! - {}" , dto);
        else log.warn ("댓글 등록 ㅅ실패 ");
        return flag;
    }

    //댓글 수정 중간 처리
    public ReplyListDto modify(ReplyUpdateDto dto) {
        replyMapper.modify(dto.toEntity());
        return getReplies(dto.getBno(), new Page(1, 10));
    }

    //댓글 삭제
    @Transactional
    public ReplyListDto remove(long boardNo) {
        long bno = replyMapper.findBno(boardNo);
        boolean flag = replyMapper.delete(boardNo);
        //삭제 후 , 댓글 번호로 원본 글번호 찾기 = boardNo fk로 존재함.
        //삭제 후 삭제된 목록을 리턴해줌.
        return flag ? getReplies(bno, new Page(1, 10)) : null;
    }
}
