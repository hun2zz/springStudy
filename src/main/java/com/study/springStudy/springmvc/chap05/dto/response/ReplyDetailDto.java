package com.study.springStudy.springmvc.chap05.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.springStudy.springmvc.chap05.entity.Reply;
import lombok.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ReplyDetailDto {
    @JsonProperty("reply_no")
    private long rno;
    private String text;
    private String writer;

//    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
    private LocalDateTime createAt;
    private String account;
    @JsonProperty("profile")
    private String profileImg;

    //엔터티를 Dto로 변환하는 생성자
    public ReplyDetailDto(ReplyFindAllDto reply) {
        this.rno = reply.getReplyNo();
        this.text = reply.getReplyText();
        this.writer = reply.getReplyWriter();
        this.createAt = reply.getReplyDate();
        this.account = reply.getAccount();
        this.profileImg = reply.getProfileImg();
    }

}
