package com.study.springStudy.springmvc.chap04.mapper;


import com.study.springStudy.springmvc.chap04.comon.Page;
import com.study.springStudy.springmvc.chap04.comon.Search;
import com.study.springStudy.springmvc.chap04.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<Board> findAll(Search page); // o


    Board findOne(int boardNum); // o

    //게시물 등록
    boolean save(Board board); // o

    //게시물 삭제.
    void delete(int boardNum); // o


    void updateLook(Board board);


    //총 게시물 수 조회
    int getCount(Search page);
}
