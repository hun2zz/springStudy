package com.study.springStudy.springmvc.chap04.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class BoardDto {
    private String title;
    private String content;
    private String writer; //작성자
}
