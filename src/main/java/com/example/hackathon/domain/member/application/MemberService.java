package com.example.hackathon.domain.member.application;

import com.example.hackathon.domain.member.domain.Member;
import com.example.hackathon.domain.member.dto.MemberResponse;
import com.example.hackathon.domain.member.repository.MemberRepository;
import com.example.hackathon.global.jwt.JwtTokenProvider;
import com.example.hackathon.global.jwt.TokenInfo;
import com.example.hackathon.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenInfo login(String username, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    public void register(String username, String password) {

        Member member = Member.of(username, password, List.of("USER"), null, null);
        memberRepository.save(member);
    }


    public MemberResponse getMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);

        if (member == null) {
            return null;
        }

        return MemberResponse.from(member);
    }

    public List<MemberResponse> getFriendList() {
        // 요청한 멤버의 id를 가져옴
        String username = SecurityUtil.getCurrentUsername();

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return null;



    }

    public MemberResponse getMember(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
    }

}