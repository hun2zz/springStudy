package com.study.springStudy.springmvc.chap05.dto.response;

import com.study.springStudy.springmvc.chap04.comon.PageMaker;
import lombok.*;
import org.checkerframework.checker.units.qual.N;

import java.util.List;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyListDto {
    private PageMaker pageInfo;
    private List<ReplyDetailDto> replies;

}
