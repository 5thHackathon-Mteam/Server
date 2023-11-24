package com.example.hackathon.domain.member.api;

import com.example.hackathon.domain.member.application.MemberService;
import com.example.hackathon.domain.member.dto.MemberLoginRequest;
import com.example.hackathon.global.jwt.TokenInfo;
import com.example.hackathon.global.util.SecurityUtil;
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

    @PostMapping("/register")
    public TokenInfo register(@RequestBody MemberLoginRequest memberLoginRequest) {
        String username = memberLoginRequest.username();
        String password = memberLoginRequest.password();
        memberService.register(username, password);
        return memberService.login(username, password);
    }

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequest memberLoginRequest) {
        String username = memberLoginRequest.username();
        String password = memberLoginRequest.password();
        return memberService.login(username, password);
    }

    @PostMapping("/whoami")
    public String whoami() {
        return SecurityUtil.getCurrentUsername();
    }
}