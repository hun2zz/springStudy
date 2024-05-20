package com.study.springStudy.springmvc.chap04.dto;


import com.study.springStudy.springmvc.chap04.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//서버에서 조회한 데이터 중 화면에 필요한 데이터만 모아놓은 클래스
@Getter
public class BoardListResponseDto {
    private String shortTitle; //줄임 처리된 제목 - 5글자 이상이면 처리
    private String shortContent; // 줄임 처리된 글 내용 - 30글자 이상이면 처리
    private String date; // 포맷팅된 날짜 문자열
    private int view;
    private int boardNo;


    //엔터티를 DTO로 변환하는 생성자
    public BoardListResponseDto(Board b) {
        this.shortTitle = makeShortTitle(b.getTitle());
        this.shortContent = makeShortContent(b.getContent());
        this.date =  dateForMatting(b.getRegDateTime());
        this.view = b.getViewCount();
        this.boardNo = b.getBoardNo();
    }

    private String dateForMatting(LocalDateTime regDateTime) {
        return DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm").format(regDateTime);
    }

    private String makeShortContent(String content) {
        return (content.length() > 30) ? content.substring(0,5) + "..." : content;
    }

    private String makeShortTitle(String title) {
        return (title.length() > 5) ? title.substring(0,5) + "..." : title;
    }

}
