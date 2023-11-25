package com.example.hackathon.domain.feed.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public class FeedImageResponse {

    private final String url;


    public static FeedImageResponse from(String s) {
        return new FeedImageResponse(s);
    }
}
