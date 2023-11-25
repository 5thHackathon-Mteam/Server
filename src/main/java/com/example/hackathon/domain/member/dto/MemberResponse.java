package com.example.hackathon.domain.member.dto;

import com.example.hackathon.domain.member.domain.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public final class MemberResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final String avatarUrl;

    @Builder
    public MemberResponse(Long id, String username, String email, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    @Builder @QueryProjection
    public MemberResponse(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.avatarUrl = member.getAvatarUrl();
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getUsername(), member.getEmail(), member.getAvatarUrl());
    }

}
