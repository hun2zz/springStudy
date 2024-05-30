package com.study.springStudy.springmvc.chap05.mapper;

import com.study.springStudy.springmvc.chap05.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    PasswordEncoder encoder;

    @Test
    @DisplayName("회원가입에 성공해야 함!")
    void saveTest() {
        //give n
        Member build = Member.builder().account("kuromi").password("abc1234!").name("쿠로미ㅏ").email("wlstkdgns@gmail.com")
                .build();
        //when
        boolean flag = memberMapper.save(build);
        //then
        assertTrue(flag);
    }


    @Test
    @DisplayName("계정명이 kuromi인 경우 회원은 중복확인 결과가  true이다.")
    void existsTest() {
        //given
        Member build = Member.builder().account("kuromi").password("abc1234!").name("쿠로미ㅏ").email("wlstkdgns@gmail.com")
                .build();

        //when
        boolean b = memberMapper.existsById("account", "kuromi");
        //then
        assertTrue(b);
    }
    @Test
    @DisplayName("계정명이 newjeans 경우 회원은 중복확인 결과가  true이다.")
    void existsTest2() {
        //given
        Member build = Member.builder().account("kuromi").password("abc1234!").name("쿠로미ㅏ").email("wlstkdgns@gmail.com")
                .build();

        //when
        boolean b = memberMapper.existsById("account", "newjeans");
        //then
        assertFalse(b);
    }

    @Test
    @DisplayName("계정명으로 찾음~")
    void findOneTest() {
        //given
        Member kuromi = memberMapper.findOne("kuromi");
        //when
        assertEquals("kuromi", kuromi.getAccount());

        //then
    }

    @Test
    @DisplayName("평문의 암호를 인코딩하여야 한다.")
    void encodingTest() {
        //given
        String rawPassword = "abc1234";
        //when
        String encode = encoder.encode(rawPassword);
        //then
        System.out.println("encode = " + encode);
    }





}