package com.study.springStudy.springmvc.chap04.dto;


import com.study.springStudy.springmvc.chap04.entity.Board;
import com.study.springStudy.springmvc.chap05.entity.Reply;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//서버에서 조회한 데이터 중 화면에 필요한 데이터만 모아놓은 클래스
@Getter
public class BoardListResponseDto {
    private String shortTitle; //줄임 처리된 제목 - 5글자 이상이면 처리
    private String shortContent; // 줄임 처리된 글 내용 - 30글자 이상이면 처리
    private String date; // 포맷팅된 날짜 문자열
    private int view;
    private int boardNo;
    private boolean hit;// hit 게시물인가 ?
    private boolean newArticle;
    private int replyCount;
    private String account;

    @Setter
    private List<Reply> replies;


    //엔터티를 DTO로 변환하는 생성자
    public BoardListResponseDto(BoardFindAllDto b) {
        this.shortTitle = makeShortTitle(b.getTitle());
        this.shortContent = makeShortContent(b.getContent());
        this.date =  dateForMatting(b.getRegDateTime());
        this.view = b.getViewCount();
        this.boardNo = (int) b.getBoardNo();
        // 게시물 등록시간
        LocalDateTime regTime = b.getRegDateTime();
        this.hit = this.view > 5;
        this.newArticle = LocalDateTime.now().isBefore(regTime.plusMinutes(5));
        this.replyCount = b.getReplyCount();
        this.account = b.getAccount();
    }

    private String dateForMatting(LocalDateTime reg) {
        return DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm").format(reg);
    }

    private String makeShortContent(String content) {
        return (content.length() > 30) ? content.substring(0,5) + "..." : content;
    }

    private String makeShortTitle(String title) {
        return (title.length() > 5) ? title.substring(0,5) + "..." : title;
    }

}
