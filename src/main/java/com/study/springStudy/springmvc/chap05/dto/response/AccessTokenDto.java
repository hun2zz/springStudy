package com.study.springStudy.springmvc.chap05.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class AccessTokenDto {
    //자동 변환이 안되기 때문에 원래 이름을 써줘야함.
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;

}
