package com.study.springStudy.springmvc.chap05.api;


import com.study.springStudy.springmvc.chap05.dto.request.SignUpDto;
import com.study.springStudy.springmvc.chap05.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


//RestController는 JSON을 보내기 때문에 jsp를 여려면 ModelAndView를 사용해야함!
//그냥 Controller에서 JSON을 보내며녀 ResponseBody를 붙여줌
@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    //회원가입 양식 열기
    @GetMapping("/sign-up")
//    @ResponseBody // 이거 붙이면 jsp가 아니라 그냥 txt가 날라감!
    // url이 jsp 경로와 같다면 void를 사용 가능.
    public void signUp() {
        log.info("/members/sign-up GET : forwarding to sign-up.jsp");
//        return "members/sign-up";
//        return "board/write";
    }

    //회원가입 요청 처리
    @PostMapping("/sign-up")
    public String signUp (@Validated SignUpDto dto) {
        log.info("/members/sign-up POST");
        log.debug("parameter : {} ", dto);

        boolean flag = memberService.join(dto);

        return flag ? "redirect:/board/list" : "redirect:/members/sign-up";
    }

    //아이디. 이메일 중복검사 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<?> check (@Param("type") String type, @Param("keyword") String keyword) {
        boolean flag = memberService.checkIdentifier(type, keyword);
        log.debug("{} 중복체크 결과 ? {}", type, flag);
        return ResponseEntity.ok().body(flag);
    }
}
