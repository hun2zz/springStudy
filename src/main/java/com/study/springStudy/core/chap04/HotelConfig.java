package com.study.springStudy.core.chap04;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//프프링 설정파일
@Configuration
//패키지 내부에 있는 @Component 붙은 객체들을 관리하겠따
@ComponentScan(basePackages = "com.study.springStudy.core")
public class HotelConfig {
}
