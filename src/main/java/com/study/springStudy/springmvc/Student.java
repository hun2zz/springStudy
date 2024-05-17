package com.study.springStudy.springmvc;

import lombok.*;

@Setter
@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor // 기본 생성자.
@AllArgsConstructor // 필드 전부 포함된 생성자.
//@RequiredArgsConstructor // 필수 파라미터 ( final 필드) 초기화 생성자!!!
public class Student {
    private String name;
    private int age;
    private int grade;
}
