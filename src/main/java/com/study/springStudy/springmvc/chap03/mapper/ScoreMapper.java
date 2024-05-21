package com.study.springStudy.springmvc.chap03.mapper;

import com.study.springStudy.springmvc.chap03.dto.RankDto;
import com.study.springStudy.springmvc.chap03.dto.ScoreDetailResponseDto;
import com.study.springStudy.springmvc.chap03.entity.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScoreMapper {
    //저장소에 데이터 추가하기
    boolean save(Score score);

    //저장소에서 데이터 전체조회하기
    List<Score> findAll(String sort);
    //저장소에서 데이터 개별조회하기
    Score findOne(long stuNum);
    RankDto findRankByStuNum(long stuNum);

     boolean delete(long sn);
    //저장소에서 국영수 점수 수정하기
     boolean updateScore(Score s);
}
