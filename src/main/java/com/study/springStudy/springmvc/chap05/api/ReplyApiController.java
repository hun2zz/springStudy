package com.study.springStudy.springmvc.chap05.api;


import com.study.springStudy.springmvc.chap04.comon.Page;
import com.study.springStudy.springmvc.chap05.dto.request.ReplyPostDto;
import com.study.springStudy.springmvc.chap05.dto.request.ReplyUpdateDto;
import com.study.springStudy.springmvc.chap05.dto.response.ReplyDetailDto;
import com.study.springStudy.springmvc.chap05.dto.response.ReplyListDto;
import com.study.springStudy.springmvc.chap05.entity.Reply;
import com.study.springStudy.springmvc.chap05.service.ReplyService;
import com.study.springStudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/replies")
@CrossOrigin
// CORS 정책 허용 범위 설정을 함.
//(origins = {"http://localhost:5500", "http://localhost:5501"})
//CrossOrigin 을 그냥 붙이면 아무나 받을 수 있음 Public
// localhost의 5500번 포트만 허용시킴.
public class ReplyApiController {
    private final ReplyService replyService;

    //댓글 목록 조회 요청
    // URL : /api/v1/replies/원본글번호    - GET - > 목록조회
    // @PathVariable : URL에 붙어있는 변수값을 읽는 아노테이션
    @GetMapping("/{bno}/page/{pageNo}")
    public ResponseEntity<?> list(@PathVariable long bno, @PathVariable int pageNo, HttpSession session) {
        if (bno == 0) {
            String s = "글 번호는 0번이 될 수 없습니다.";
            log.warn(s);

            return ResponseEntity.badRequest().body(s);
        }
        log.info("/api/v1/replies/{}", bno);
        ReplyListDto replies = replyService.getReplies(bno, new Page(pageNo, 10));
        String auth = LoginUtil.getAuth(session);
        replies.setAuth(auth);
        replies.setAccount(LoginUtil.getLoggedUser(session));
        return ResponseEntity.ok().body(replies);
    }

    //댓글 생성 요청
    // @RequestBody : 클라이언트가 전송한 데이터를 JSON 으로 받아서 파싱
    @PostMapping
    public ResponseEntity<?> posts (@Validated @RequestBody ReplyPostDto dto
    , BindingResult result, HttpSession session) {  // BindingResult : 입력값 검증 결과 데이터를 갖고있는 객체


        log.info("api/v1/replies : post ");
        log.debug("parameter : {}" , dto);

        if(result.hasErrors()) {
            Map<String, String> erros = makeValidatonMessageMap(result);
            return ResponseEntity.badRequest().body(erros);
        }
        boolean flag = replyService.register(dto, session);
        if (!flag) return ResponseEntity.internalServerError().body("댓글 등록 실패");
        return ResponseEntity.ok().body(replyService.getReplies(dto.getBno(), new Page(1, 10)));
    }

    //댓글 수정 요청
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})//
    // put과 patch 매핑의 차이는 put은 전체수정, patch는 일부수정임.
//    ex
    /*
        const obj = {
            age : 3,
            }

        put - obj = {}; 객체를 아예 새로 갈아끼움
        patch - obj.age = 10; 으로 수정함! - 객체의 프로퍼티만 조작함.
     */
    public ResponseEntity<?> update(@Validated @RequestBody ReplyUpdateDto dto, BindingResult result) {

        log.info("api/v1/replies : PUT, PATCH ");
        log.debug("parameter : {}" , dto);

        if(result.hasErrors()) {
            Map<String, String> erros = makeValidatonMessageMap(result);
            return ResponseEntity.badRequest().body(erros);
        }
        ReplyListDto modify = replyService.modify(dto);
        return ResponseEntity.ok().body(modify);
    }



    private Map<String, String> makeValidatonMessageMap(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        //에러 정보가 모여있는 리스트
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError error : fieldErrors) {
//            field는 에러 코드, message는 원인을 보내줌.
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }

    //삭제 처리 요청
    @DeleteMapping("/{rno}")
    public ResponseEntity<?> delete(@PathVariable long rno) {
        ReplyListDto dtoList = replyService.remove(rno);
        return ResponseEntity.ok().body(dtoList);
    }
}
