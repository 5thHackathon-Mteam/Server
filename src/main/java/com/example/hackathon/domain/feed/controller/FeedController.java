package com.example.hackathon.domain.feed.controller;

import com.example.hackathon.domain.feed.dto.FeedCreateRequest;
import com.example.hackathon.domain.feed.dto.FeedResponse;
import com.example.hackathon.domain.feed.dto.FeedUpdateRequest;
import com.example.hackathon.domain.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void test(@ModelAttribute FeedCreateRequest feedCreateRequest) {
        feedService.create(feedCreateRequest);
    }

    @GetMapping(value = "/latest")
    public ResponseEntity<List<FeedResponse>> getFeedList(@RequestParam(required = false) Long cursorId,
                                                            @RequestParam int pageSize) {
        return ResponseEntity.ok()
                .body(feedService.getFeedList(cursorId, pageSize));
    }

    @PatchMapping(value = "/{feedId}")
    public ResponseEntity<FeedResponse> updateFeed(@PathVariable Long feedId,
                           @RequestBody FeedUpdateRequest feedUpdateRequest) {
        return ResponseEntity.ok()
                .body(feedService.update(feedId, feedUpdateRequest));
    }
}
