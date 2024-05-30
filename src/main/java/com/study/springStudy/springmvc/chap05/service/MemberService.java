package com.study.springStudy.springmvc.chap05.service;


import com.study.springStudy.springmvc.chap05.dto.request.LoginDto;
import com.study.springStudy.springmvc.chap05.dto.request.SignUpDto;
import com.study.springStudy.springmvc.chap05.entity.Member;
import com.study.springStudy.springmvc.chap05.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.study.springStudy.springmvc.chap05.service.LoginResult.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;

    //회원 가입 중간 처리
    public boolean join(SignUpDto dto) {
        // dto를 엔터티로 변환
        Member member = dto.toEntity();
        member.setPassword(encoder.encode(dto.getPassword()));

        //비밀번호를 인코딩 ( 암호화 )

        return memberMapper.save(member);
    }


    //로그인 검증 처리

    public LoginResult authenticate(LoginDto dto) {
        //회원가입 여부 확인
        Member foundMember = memberMapper.findOne(dto.getAccount());
        if(foundMember == null) {
            log.info("{} - 회원가입이 필요합니다.", dto.getAccount());
            return NO_ACC;
        }

        //비밀번호 일치 검사

        String inputPassword = dto.getPassword(); // 클라이언트에서 입력한 비번
        String originPassword = foundMember.getPassword(); // db에서 암호화된 패스워드


        //passwordEncoder에서는 암호화된 비번을 내부적으로 비교해주는 기능을 제공함.
        if(!encoder.matches(inputPassword, originPassword)) {
            log.info("비밀번호가 일치하지 않습니다.");
            return NO_PW;
        }

        log.info("{}님 로그인 성공", foundMember.getName());
        return SUCCESS;
    }
}
