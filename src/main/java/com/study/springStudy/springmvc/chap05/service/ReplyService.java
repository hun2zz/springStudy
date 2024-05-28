package com.study.springStudy.springmvc.chap05.service;


import com.study.springStudy.springmvc.chap05.dto.request.ReplyPostDto;
import com.study.springStudy.springmvc.chap05.dto.response.ReplyDetailDto;
import com.study.springStudy.springmvc.chap05.entity.Reply;
import com.study.springStudy.springmvc.chap05.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyService {
    public final ReplyMapper replyMapper;

    //댓글 목록 전체 조회
    public List<ReplyDetailDto> getReplies(long boardNo) {
        List<Reply> all = replyMapper.findAll(boardNo);
        return all.stream().map(r->new ReplyDetailDto(r)).collect(Collectors.toList());
    }

    //댓글 입력
    public boolean register(ReplyPostDto dto) {
        Reply reply = Reply.builder().replyText(dto.getText()).replyWriter(dto.getAuthor()).boardNo(dto.getBno()).build();
        boolean flag = replyMapper.save(reply);
        if (flag ) log.info("댓글 등록 성공 !! - {}" , dto);
        else log.warn ("댓글 등록 ㅅ실패 ");
        return flag;
    }

    //댓글 수정
    public void modify() {

    }

    //댓글 삭제
    @Transactional
    public List<ReplyDetailDto> remove(long boardNo) {
        long bno = replyMapper.findBno(boardNo);
        boolean flag = replyMapper.delete(boardNo);
        //삭제 후 , 댓글 번호로 원본 글번호 찾기 = boardNo fk로 존재함.
        //삭제 후 삭제된 목록을 리턴해줌.
        return flag ? getReplies(bno) : Collections.emptyList();

    }
}
