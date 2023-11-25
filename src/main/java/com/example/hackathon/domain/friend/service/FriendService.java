package com.example.hackathon.domain.friend.service;

import com.example.hackathon.domain.friend.domain.Friend;
import com.example.hackathon.domain.friend.dto.CreateFriendRequest;
import com.example.hackathon.domain.friend.repository.FriendRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    public void requestFriend(CreateFriendRequest createFriendRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        String toUserEmail = createFriendRequest.getTargetEmail();

        Friend currentToTarget = Friend.builder()
                .fromUserEmail(currentEmail)
                .toUserEmail(toUserEmail)
                .areWeFriend(true)
                .build();

        Friend targetToCurrent = Friend.builder()
                .fromUserEmail(toUserEmail)
                .toUserEmail(currentEmail)
                .areWeFriend(false)
                .build();

        friendRepository.saveAll(List.of(currentToTarget, targetToCurrent));
    }
}
