package com.study.springStudy.springmvc.chap03.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScoreModifyRequestDto {

    private long stuNum ;
    private int kor, eng, math;


}
