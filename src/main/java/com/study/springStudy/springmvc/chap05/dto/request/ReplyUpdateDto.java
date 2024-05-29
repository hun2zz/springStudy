package com.study.springStudy.springmvc.chap05.dto.request;

import com.study.springStudy.springmvc.chap05.entity.Reply;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class ReplyUpdateDto {
    @NotBlank
    @Size(min = 1, max = 300)
    private String newText; // 새로운 댓글 내용
    @NotNull
    private Long rno;  // 수정할 댓글의 댓글번호
    @NotNull
    private long bno; //수정 완료 후 새로운 목록을 조회하기 위해서 bno도 받음!!

    //엔터티로 변환하는 메서드
    public Reply toEntity () {
        return Reply.builder().replyText(this.newText).replyNo(this.rno).boardNo(this.bno).build();
    }
}
