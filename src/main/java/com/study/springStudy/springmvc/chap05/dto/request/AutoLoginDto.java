package com.study.springStudy.springmvc.chap05.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutoLoginDto {
    private String sessionId; // 자동 로그인 쿠키값
    private LocalDateTime limitTime; // 만료 시간
    private String account; // 계정명

}
