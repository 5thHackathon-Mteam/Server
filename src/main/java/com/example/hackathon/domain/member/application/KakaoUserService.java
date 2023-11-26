package com.example.hackathon.domain.member.application;

import com.example.hackathon.domain.member.domain.Member;
import com.example.hackathon.domain.member.dto.SocialUserInfoResponse;
import com.example.hackathon.domain.member.repository.MemberRepository;
import com.example.hackathon.global.jwt.JwtTokenProvider;
import com.example.hackathon.global.jwt.TokenInfo;
import com.example.hackathon.global.properties.OauthProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoUserService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final OauthProperties oauthProperties;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public SocialUserInfoResponse kakaoLogin(String code, String redirect_uri, HttpServletResponse response) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code, redirect_uri);

        // 2. 토큰으로 카카오 API 호출
        SocialUserInfoResponse kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 카카오ID로 회원가입 처리
        Member kakaoUser = registerKakaoUserIfNeed(kakaoUserInfo);

        // 4. 강제 로그인 처리
        Authentication authentication = forceLogin(kakaoUser);

        // 5. response Header, Body에 JWT 토큰 추가
        return kakaoUsersAuthorizationInput(authentication, kakaoUser);
    }

    private String getAccessToken(String code, String redirect_uri) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", oauthProperties.getClientId());
        body.add("redirect_uri", redirect_uri);
        body.add("client_secret", oauthProperties.getClientSecret());
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private SocialUserInfoResponse getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        return handleKakaoResponse(response.getBody());
    }

    private SocialUserInfoResponse handleKakaoResponse(String responseBody) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode.get("id").asLong();

        String nickname = jsonNode.get("properties")
                .get("nickname").asText();

        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        return SocialUserInfoResponse.of(id, nickname, email);
    }

    private Member registerKakaoUserIfNeed (SocialUserInfoResponse kakaoUserInfo) {
        String userId = kakaoUserInfo.getId().toString();
        String nickname = kakaoUserInfo.getNickname();
        String email = kakaoUserInfo.getEmail();
        Member kakaoUser = memberRepository.findByUserId(userId)
                .orElse(null);

        if (kakaoUser == null) {
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            kakaoUser = Member.builder()
                    .userId(userId)
                    .username(nickname)
                    .password(encodedPassword)
                    .roles(List.of("USER"))
                    .email(email)
                    .build();

            memberRepository.save(kakaoUser);

        }
        return kakaoUser;
    }

    private Authentication forceLogin(Member kakaoUser) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), kakaoUser.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private SocialUserInfoResponse kakaoUsersAuthorizationInput(Authentication authentication, Member kakaoUser) {
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return SocialUserInfoResponse.of(
                kakaoUser.getId(),
                kakaoUser.getUsername(),
                kakaoUser.getEmail(),
                tokenInfo
        );
    }
}