package com.study.springStudy.springmvc.chap05.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.springStudy.springmvc.chap04.comon.PageMaker;
import lombok.*;
import org.checkerframework.checker.units.qual.N;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyListDto {
    private PageMaker pageInfo;
    private List<ReplyDetailDto> replies;
    @Setter
    private String account;
    @Setter
    private String auth;
    @Setter
//    @JsonProperty("profile")
    private String profileImg;

}
