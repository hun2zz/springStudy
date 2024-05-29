package com.study.springStudy.springmvc.chap05.mapper;


import com.study.springStudy.springmvc.chap04.comon.Page;
import com.study.springStudy.springmvc.chap05.dto.request.ReplyUpdateDto;
import com.study.springStudy.springmvc.chap05.dto.response.ReplyDetailDto;
import com.study.springStudy.springmvc.chap05.entity.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {
    //댓글 등록
    boolean save(Reply reply);

    //댓글 수정
    boolean modify(ReplyUpdateDto reply);

    //댓글 삭제
    boolean delete(long replyNo);

    // 내가 선택한 게시물에 달린 댓글 목록 조회
    List<Reply> findAll(@Param("bno")long replyNo,@Param("p") Page page);

    //총 댓글 수 조회
    int count(long boardNo);


    //댓글 번호로 원본 글번호 찾기
    long findBno(long boardNo);
}
