package com.study.springStudy.springmvc.chap05.service;


import com.study.springStudy.springmvc.chap05.dto.request.AutoLoginDto;
import com.study.springStudy.springmvc.chap05.dto.request.LoginDto;
import com.study.springStudy.springmvc.chap05.dto.request.SignUpDto;
import com.study.springStudy.springmvc.chap05.dto.response.LoginUserInfoDto;
import com.study.springStudy.springmvc.chap05.entity.Member;
import com.study.springStudy.springmvc.chap05.mapper.MemberMapper;
import com.study.springStudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;

import static com.study.springStudy.springmvc.chap05.service.LoginResult.*;
import static com.study.springStudy.springmvc.util.LoginUtil.AUTO_LOGIN_COOKIE;
import static com.study.springStudy.springmvc.util.LoginUtil.LOGIN;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;
    private final ReplyService replyService;

    //회원 가입 중간 처리
    public boolean join(SignUpDto dto) {
        // dto를 엔터티로 변환
        Member member = dto.toEntity();
        member.setPassword(encoder.encode(dto.getPassword()));

        //비밀번호를 인코딩 ( 암호화 )

        return memberMapper.save(member);
    }


    //로그인 검증 처리

    public LoginResult authenticate(LoginDto dto, HttpSession session, HttpServletResponse rs) {
        //회원가입 여부 확인
        String account = dto.getAccount();
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

        //자동 로그인 추가 처리
        if(dto.isAutoLogin()) {
            //1. 자동 로그인 쿠키 생성
            // - 쿠키 내부에 절대로 중복되지 않는 유니크한 값을 저장
            // (UUID, SessionID)
            String id = session.getId();
            Cookie autoLoginCookie = new Cookie(AUTO_LOGIN_COOKIE, id);

            //쿠키 설정
            autoLoginCookie.setPath("/"); // 쿠키를 사용ㅎㄹ 경로
            autoLoginCookie.setMaxAge(60 * 60 * 24 * 90); // 자동 로그인 유지 기간

            //2. 쿠키를 클라이언트에 전송 - 응답바디에 실어보내야함.
            rs.addCookie(autoLoginCookie);



            //3. DB에 해당 쿠키값을 저장
            memberMapper.updateAutoLogin(AutoLoginDto.builder().
                    sessionId(id)
                            .limitTime(LocalDateTime.now().plusDays(90))
                            .account(account)
                    .build());

        }

        log.info("{}님 로그인 성공", foundMember.getName());
        //세션의 수명 : 설정된 시간 OR 브라우저를 닫기 전까지
        session.setMaxInactiveInterval(3600);
        int maxInactiveInterval = session.getMaxInactiveInterval();
        log.debug("session time : {}", maxInactiveInterval);
        session.setAttribute(LOGIN, new LoginUserInfoDto(foundMember));
        return SUCCESS;
    }

    //아이디, 이메일 중복검사
    public boolean checkIdentifier(@Param("type") String type, @Param("keyword") String keyword) {
        System.out.println("타입은! = " + type);
        System.out.println("키워드는! = " + keyword);
        return memberMapper.existsById(type, keyword);
    }
}
