package com.example.hackathon.domain.member.dto;

import com.example.hackathon.global.jwt.TokenInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserInfoResponse {
    @Schema(description = "DB 식별용 아이디", example = "1")
    private Long id;
    @Schema(description = "닉네임", example = "tester")
    private String nickname;

    @Schema(description = "이메일")
    private String email;

    private String accessToken;


    public static SocialUserInfoResponse of(Long id, String nickname, String email) {
        return SocialUserInfoResponse.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .build();
    }
    public static SocialUserInfoResponse of(Long id, String nickname, String email, TokenInfo tokenInfo) {
        return SocialUserInfoResponse.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .accessToken(tokenInfo.accessToken())
                .build();
    }

}