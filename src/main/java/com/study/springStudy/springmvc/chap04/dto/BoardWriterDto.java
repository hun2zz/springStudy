package com.study.springStudy.springmvc.chap04.dto;

import com.study.springStudy.springmvc.chap04.entity.Board;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BoardWriterDto {
    private String writer;
    private String title;
    private String content;
    private String account;

    public Board toEntity() {
        Board b = new Board();
        b.setContent(this.content);
        b.setWriter(this.writer);
        b.setTitle(this.title);
        return b;
    }
    }
