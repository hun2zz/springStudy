package com.study.springStudy.springmvc.chap05.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
//회원가입에 사용할 객체
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Setter
public class ProfileUpdateDto {
    public String account;
    private MultipartFile profileImage;
}
