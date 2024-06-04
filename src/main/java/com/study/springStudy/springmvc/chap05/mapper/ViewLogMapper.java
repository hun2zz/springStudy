package com.study.springStudy.springmvc.chap05.mapper;


import com.study.springStudy.springmvc.chap05.entity.ViewLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

@Mapper
public interface ViewLogMapper {
    // 조회수 기록 추가
    void insertViewLog(ViewLog viewLog);

    //조회수 기록 시간 수정
    void updateViewLog(ViewLog viewLog);

    //조회수 기록 조회
    ViewLog findOne(@Param("account") String account, @Param("num") long bno);
}
