package com.study.springStudy.springmvc.chap05.api;


import com.study.springStudy.springmvc.chap05.dto.request.ReplyPostDto;
import com.study.springStudy.springmvc.chap05.dto.response.ReplyDetailDto;
import com.study.springStudy.springmvc.chap05.entity.Reply;
import com.study.springStudy.springmvc.chap05.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{bno}")
    public ResponseEntity<?> list(@PathVariable long bno) {
        if (bno == 0) {
            String s = "글 번호는 0번이 될 수 없습니다.";
            log.warn(s);

            return ResponseEntity.badRequest().body(s);
        }
        log.info("/api/v1/replies/{}", bno);
        List<ReplyDetailDto> replies = replyService.getReplies(bno);
//        log.debug("first reply : {}", replies.get(0));
        return ResponseEntity.ok().body(replies);
    }

    //댓글 생성 요청
    // @RequestBody : 클라이언트가 전송한 데이터를 JSON 으로 받아서 파싱
    @PostMapping
    public ResponseEntity<?> posts (@Validated @RequestBody ReplyPostDto dto
    , BindingResult result) {  // BindingResult : 입력값 검증 결과 데이터를 갖고있는 객체


        log.info("api/v1/replies : post ");
        log.debug("parameter : {}" , dto);

        if(result.hasErrors()) {
            Map<String, String> erros = makeValidatonMessageMap(result);
            return ResponseEntity.badRequest().body(erros);
        }
        boolean flag = replyService.register(dto);
        if (!flag) return ResponseEntity.internalServerError().body("댓글 등록 실패");
        return ResponseEntity.ok().body(replyService.getReplies(dto.getBno()));
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
        List<ReplyDetailDto> dtoList = replyService.remove(rno);
        return ResponseEntity.ok().body(dtoList);
    }
}
