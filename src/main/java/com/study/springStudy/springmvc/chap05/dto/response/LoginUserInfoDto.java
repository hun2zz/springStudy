package com.study.springStudy.springmvc.chap05.dto.response;

import com.study.springStudy.springmvc.chap05.entity.Member;
import lombok.*;
import org.checkerframework.checker.units.qual.A;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginUserInfoDto {
    //클라이언트에 보낼 정보
    private String account;
    private String nickName;
    private String email;
    private String auth;
    private String profileImg;

    public LoginUserInfoDto(Member member) {
        this.account = member.getAccount();
        this.email = member.getEmail();
        this.nickName= member.getName();
        this.auth = member.getAuth().name();
        this.profileImg = member.getProfileImg();
    }
}
