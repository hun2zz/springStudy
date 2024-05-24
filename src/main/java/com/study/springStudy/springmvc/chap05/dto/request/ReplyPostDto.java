package com.study.springStudy.springmvc.chap05.dto.request;


import lombok.*;

@Getter @ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class ReplyPostDto {
    private String text;
    private String author; // 작성자
    private Long bno;
}
