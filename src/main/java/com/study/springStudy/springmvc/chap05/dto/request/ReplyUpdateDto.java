package com.study.springStudy.springmvc.chap05.dto.request;

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
    private String replyText;
    @NotNull
    private Long replyNo;
}
