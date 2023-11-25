package com.example.hackathon.domain.friend.controller;

import com.example.hackathon.domain.friend.dto.CreateFriendRequest;
import com.example.hackathon.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/request")
    public void requestFriend(@RequestBody CreateFriendRequest createFriendRequest) {
        friendService.requestFriend(createFriendRequest);
    }

    @PostMapping("/accept")
    public void acceptFriend(@RequestBody CreateFriendRequest createFriendRequest) {
        friendService.acceptFriend(createFriendRequest);
    }
}
