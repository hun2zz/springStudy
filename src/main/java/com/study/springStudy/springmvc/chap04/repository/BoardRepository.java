package com.study.springStudy.springmvc.chap04.repository;


import com.study.springStudy.springmvc.chap04.entity.Board;

import java.util.List;

// 게시판의 CRUD 기능을 생성하는 클래스.
public interface BoardRepository {


    //게시물 목록 조회

    //게시글 상세 조회
    default Board findOne(int boardNum) {
        return null;
    }

    //게시물 등록
    default boolean save(Board board) {
        return false;
    }

    //게시물 삭제.
    default boolean delete(int boardNum) {
        return false;
    }

    List<Board> findAll(String sort);

    boolean updateLook(Board board, int num);

}
