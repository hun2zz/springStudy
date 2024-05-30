package com.study.springStudy.springmvc.chap05.service;

import com.study.springStudy.springmvc.chap05.dto.request.LoginDto;
import com.study.springStudy.springmvc.chap05.dto.request.SignUpDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService  memberService;

    @Test
    @DisplayName("회원가입을 하면 비밀번호가 인코딩")
    void joinTest() {
        //given
        SignUpDto build = SignUpDto.builder().account("kitty").password("kkk1234")
                .email("sanrio@abc.def").name("헬로키티").build();
        //when
        boolean flag = memberService.join(build);

        //then
        assertTrue(flag);
    }


    @Test
    @DisplayName("id가 존재하지 않는 경우를 테스트함.")
    void noAccTest() {
        //given
        LoginDto dto = LoginDto.builder().account("kitty123").password("asdasdasd").build();
        //when
        LoginResult result = memberService.authenticate(dto);
        //then

        assertEquals(LoginResult.NO_ACC, result);
    }


    @Test
    @DisplayName("pw가 일치하지 않는 경우를 테스트함.")
    void noPwTest() {
        //given
        LoginDto dto = LoginDto.builder().account("kitty").password("asdaasasd").build();
        //when
        LoginResult result = memberService.authenticate(dto);
        //then

        assertEquals(LoginResult.NO_PW, result);
    }

    @Test
    @DisplayName("로그인이 성공한 경우를 테스트함.")
    void successTest() {
        //given
        LoginDto dto = LoginDto.builder().account("kitty").password("kkk1234").build();
        //when
        LoginResult result = memberService.authenticate(dto);
        //then

        assertEquals(LoginResult.SUCCESS, result);
    }


}