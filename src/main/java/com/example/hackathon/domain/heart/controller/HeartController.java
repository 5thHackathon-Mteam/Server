package com.example.hackathon.domain.heart.controller;

import com.example.hackathon.domain.heart.dto.HeartCountResponse;
import com.example.hackathon.domain.heart.dto.HeartRequest;
import com.example.hackathon.domain.heart.service.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/{feedId}")
    public ResponseEntity<Boolean> heart(@RequestBody HeartRequest request) {
        return ResponseEntity.ok().body(heartService.manageHeart(request.feedId(), request.memberId()));
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<HeartCountResponse> heartCount(@PathVariable Long feedId) {
        return ResponseEntity.ok().body(heartService.heartCount(feedId));
    }
}
