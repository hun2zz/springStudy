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
    private String account;
    @Setter
    private String password;
    private String name;
    private String  email;
    private Auth auth;
    private LocalDateTime regDate;



}
