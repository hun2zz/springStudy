package com.study.springStudy.springmvc.chap05.dto.request;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class ReplyPostDto {

    /*
        notnull : null은 허용 안됨
        notempty : null은 되는데 빈 문자는 안됨
        notblank : null도 안되고 빈 문자도 안됨.
     */
    @NotBlank
    @Size(min = 1, max = 300)
    private String text;
    @NotBlank
    @Size(min = 2, max = 8)
    private String author; // 작성자
    @NotNull
    private Long bno;
}
