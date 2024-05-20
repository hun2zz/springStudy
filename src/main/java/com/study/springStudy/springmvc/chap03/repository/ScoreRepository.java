package com.study.springStudy.springmvc.chap03.repository;

import com.study.springStudy.springmvc.chap03.entity.Score;

import java.util.List;

//역할 : 적당한 저장소에 CRUD 하기 .
public interface ScoreRepository {


    //저장소에 데이터 추가하기
    boolean save(Score score);

    //저장소에서 데이터 전체조회하기
    List<Score> findAll(String sort);
    //저장소에서 데이터 개별조회하기
    Score findOne(long stuNum);
    int[] findRankByStuNum(long stuNum);

    default boolean delete(long sn) {
        return false;
    }
    //저장소에서 국영수 점수 수정하기
    default boolean updateScore(Score s) {
        return false;
    };

}
