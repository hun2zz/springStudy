package com.study.springStudy.springmvc.chap03.dto;


import com.study.springStudy.springmvc.chap03.entity.Score;
import lombok.Getter;

@Getter
public class ScoreListResponseDto {

    private long stuNum;
    private String maskingName; // 첫글자 빼고 모두 * 처리
    private double average;
    private String grade;

    public ScoreListResponseDto(Score s) {
        this.stuNum = s.getStuNum();
        this.maskingName = makeMaskingName(s.getStuName());
        this.average = s.getAverage();
        this.grade = s.getGrade().toString();
    }

    private String makeMaskingName(String stuName) {
        char firstLetter = stuName.charAt(0); // 첫 글자만 가져오기!!
        String maskingName = "" + firstLetter;
        for (int i = 0; i <stuName.length()-1; i++) {
            maskingName += "*";
        }
        return maskingName;
    }
}
