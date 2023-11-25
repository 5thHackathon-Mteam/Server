package com.example.hackathon.domain.friend.controller;

import com.example.hackathon.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/request-friend")
    public void requestFriend() {
        friendService.requestFriend();
    }
}
