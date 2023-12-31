package com.example.hackathon.domain.feed.controller;

import com.example.hackathon.domain.coment.dto.CommentResponse;
import com.example.hackathon.domain.coment.service.CommentService;
import com.example.hackathon.domain.feed.dto.FeedCreateRequest;
import com.example.hackathon.domain.feed.dto.FeedImageResponse;
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
    private final CommentService commentService;

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

    @GetMapping(value = "/images")
    public ResponseEntity<List<FeedImageResponse>> getFeedImages(@RequestParam Long feedId) {
        return ResponseEntity.ok()
                .body(feedService.getFeedImages(feedId));
    }


    @GetMapping("/comments/{feedId}")
    public ResponseEntity<List<CommentResponse>> getComment(@PathVariable Long feedId) {
        return ResponseEntity.ok()
                .body(commentService.getCommentByFeedId(feedId));
    }

    @PatchMapping(value = "/update/{feedId}")
    public ResponseEntity<FeedResponse> updateFeed(@PathVariable Long feedId,
                           @RequestBody FeedUpdateRequest feedUpdateRequest) {
        return ResponseEntity.ok()
                .body(feedService.updateFeed(feedId, feedUpdateRequest));
    }

    @PostMapping(value = "/delete/{feedId}")
    public ResponseEntity<Boolean> deleteFeed(@PathVariable Long feedId) {
        return ResponseEntity.ok()
                .body(feedService.deleteFeed(feedId));
    }

    @GetMapping(value = "member/{memberId}")
    public ResponseEntity<List<FeedResponse>> getFeedListByMemberId(@PathVariable Long memberId,
                                                                    @RequestParam(required = false) Long cursorId,
                                                                    @RequestParam int pageSize) {
        return ResponseEntity.ok()
                .body(feedService.getFeedListByMemberId(memberId, cursorId, pageSize));
    }

}
