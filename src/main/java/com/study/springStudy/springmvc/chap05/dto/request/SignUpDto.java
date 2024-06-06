package com.study.springStudy.springmvc.chap05.dto.request;


import com.study.springStudy.springmvc.chap05.entity.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//회원가입에 사용할 객체
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Setter
public class SignUpDto {
    @NotBlank(message = "아이디 필수임")
    @Size(min = 4, max = 14, message = "아이디는 4~14글자")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문과 숫자만 포함해야 합니다.")
    private String account;

    @NotBlank
    private String password;

    @NotBlank
    @Size(min = 2, max = 6)
    private String name;

    @NotBlank
    @Email
    private String email;

    //프로필 사진 데이터
    private MultipartFile profileImage;

    public Member toEntity() {
        return Member.builder().account(this.account).password(this.password)
                .email(this.email).name(this.name).build();
    }
}
