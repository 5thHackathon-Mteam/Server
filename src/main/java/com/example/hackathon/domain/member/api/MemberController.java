package com.example.hackathon.domain.member.api;

import com.example.hackathon.domain.member.application.MemberService;
import com.example.hackathon.domain.member.dto.MemberLoginRequest;
import com.example.hackathon.global.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequest memberLoginRequest) {
        String memberId = memberLoginRequest.memberId();
        String password = memberLoginRequest.password();
        TokenInfo tokenInfo = memberService.login(memberId, password);
        return tokenInfo;
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}