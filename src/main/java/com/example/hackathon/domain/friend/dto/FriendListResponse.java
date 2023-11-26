package com.example.hackathon.domain.friend.dto;

import com.example.hackathon.domain.member.domain.Member;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FriendListResponse {
    private List<MemberDto> friends;
}
