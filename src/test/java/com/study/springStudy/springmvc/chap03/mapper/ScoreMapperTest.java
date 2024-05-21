package com.study.springStudy.springmvc.chap03.mapper;

import com.study.springStudy.springmvc.chap03.dto.RankDto;
import com.study.springStudy.springmvc.chap03.dto.ScoreDetailResponseDto;
import com.study.springStudy.springmvc.chap03.entity.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ScoreMapperTest {

    @Autowired
    ScoreMapper scoreMapper;


    @Test
    @DisplayName("전체조회")
    void fiindAllTest() {
        //given
        List<Score> all = scoreMapper.findAll(null);
        //when

        //then
        all.forEach(System.out::println);
    }

    @Test
    @DisplayName("개별조회")
    void findOneTest() {
        //given
        long stuNum= 12;
        Score one = scoreMapper.findOne(stuNum);
        System.out.println(one);
        //when

        //then
    }


    @Test
    @DisplayName("순위 조회")
    void rankTEst() {
        //given
        long stuNum= 17;

        //when
        RankDto rankByStuNum = scoreMapper.findRankByStuNum(stuNum);

        //then
        System.out.println(rankByStuNum);
    }

}