package com.example.hackathon.domain.member.api;

import com.example.hackathon.domain.member.application.MemberService;
import com.example.hackathon.domain.member.dto.MemberLoginRequest;
import com.example.hackathon.global.jwt.TokenInfo;
import com.example.hackathon.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TokenInfo> register(@RequestBody MemberLoginRequest memberLoginRequest) {
        String username = memberLoginRequest.username();
        String password = memberLoginRequest.password();
        memberService.register(username, password);

        return ResponseEntity.ok()
                .body(memberService.login(username, password));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        String username = memberLoginRequest.username();
        String password = memberLoginRequest.password();
        return ResponseEntity.ok()
                .body(memberService.login(username, password));
    }

    @Operation(summary = "test if token works", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok()
                .body("you are authenticated");
    }

    @Operation(summary = "whoami", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/whoami")
    public ResponseEntity<String> whoami() {
        return ResponseEntity.ok()
                .body(SecurityUtil.getCurrentUsername());
    }

}