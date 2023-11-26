package com.example.hackathon.domain.member.api;

import com.example.hackathon.domain.member.application.KakaoUserService;
import com.example.hackathon.domain.member.dto.SocialUserInfoResponse;
import com.example.hackathon.global.jwt.TokenInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final KakaoUserService kakaoUserService;

    // 카카오 로그인
    @Operation(summary = "카카오 로그인", description = "카카오 로그인으로 토큰을 쿠키로 응답받습니다.")
    @GetMapping("/oauth/kakao")
    public ResponseEntity<SocialUserInfoResponse> kakaoLogin(@Parameter(name = "code", description = "카카오 로그인을 위한 코드", required = true)
                                        @RequestParam String code,
                                                             @Parameter(name= "redirect_uri", description = "카카오 로그인을 위한 리다이렉트 uri", required = true)
                                        @RequestParam String redirect_uri,
                                                             HttpServletResponse response) throws JsonProcessingException {
        System.out.println("code = " + code);
        return ResponseEntity.ok()
                .body(kakaoUserService.kakaoLogin(code, redirect_uri, response));
    }

}
