package com.example.hackathon.domain.friend.controller;

import com.example.hackathon.domain.friend.dto.CreateFriendRequest;
import com.example.hackathon.domain.friend.dto.FriendListResponse;
import com.example.hackathon.domain.friend.dto.FriendRequest;
import com.example.hackathon.domain.friend.service.FriendService;
import java.nio.file.attribute.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/member/friend")
    public ResponseEntity<FriendListResponse> sd(@RequestBody FriendRequest friendRequest) {
        return ResponseEntity.ok().body(friendService.getFriendList(friendRequest));
    }

    @PostMapping("/friend/request")
    public void requestFriend(@RequestBody CreateFriendRequest createFriendRequest) {
        friendService.requestFriend(createFriendRequest);
    }

    @PostMapping("/friend/accept")
    public void acceptFriend(@RequestBody CreateFriendRequest createFriendRequest) {
        friendService.acceptFriend(createFriendRequest);
    }
}
