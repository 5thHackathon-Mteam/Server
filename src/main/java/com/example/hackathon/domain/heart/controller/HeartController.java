package com.example.hackathon.domain.heart.controller;

import com.example.hackathon.domain.heart.service.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("{feedId}")
    public ResponseEntity<Boolean> heart(@PathVariable Long feedId) {
        heartService.manageHeart(feedId);
        return ResponseEntity.ok().body(true);
    }
}
