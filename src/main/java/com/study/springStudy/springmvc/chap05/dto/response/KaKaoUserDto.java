package com.study.springStudy.springmvc.chap05.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Properties;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KaKaoUserDto {
    private long id;

    @JsonProperty("connected_at")
    private LocalDateTime connectedAt;

    private Properties properties;


    @Getter @ToString // 객체를 사용하기 위한 내부 클래스
    public static class Properties {
        private String nickname;
        @JsonProperty("profile_image")
        private String profileImage;

    }
}
