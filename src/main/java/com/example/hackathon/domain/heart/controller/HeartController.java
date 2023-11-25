package com.example.hackathon.domain.heart.controller;

import com.example.hackathon.domain.heart.dto.HeartRequest;
import com.example.hackathon.domain.heart.service.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {

    private final HeartService heartService;

    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody HeartRequest heartRequest) throws Exception {
        heartService.save(heartRequest);
        return ResponseEntity.ok()
                .body(true);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> delete(@RequestBody HeartRequest HeartRequest) {
        heartService.delete(HeartRequest);
        return ResponseEntity.ok()
                .body(true);
    }
}
