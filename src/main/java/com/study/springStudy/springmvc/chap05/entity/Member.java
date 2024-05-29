package com.study.springStudy.springmvc.chap05.entity;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    private int account;
    private int password;
    private int name;
    private int email;
    private Auth auth;
    private LocalDateTime regDate;



}
