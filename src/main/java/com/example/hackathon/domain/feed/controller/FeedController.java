package com.example.hackathon.domain.feed.controller;

import com.example.hackathon.domain.feed.dto.FeedCreateRequest;
import com.example.hackathon.domain.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping(value = "/feed", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void test(@ModelAttribute FeedCreateRequest feedCreateRequest) {
        feedService.create(feedCreateRequest);
    }
}
