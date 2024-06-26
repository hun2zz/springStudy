package com.study.springStudy.springmvc.chap04.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardFindAllDto {

    private long boardNo;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDateTime;
    private int viewCount;
    private int replyCount;
    private String account;
}
