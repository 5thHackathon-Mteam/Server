package com.example.hackathon.domain.feed.dto;

import com.example.hackathon.domain.feed.domain.Feed;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class FeedResponse {
    private Long id;
    private String gptContent;

    @Builder @QueryProjection
    public FeedResponse(Long id, String gptContent) {
        this.id = id;
        this.gptContent = gptContent;
    }

    public static FeedResponse from(Feed feed) {
        return FeedResponse.builder()
                .id(feed.getId())
                .gptContent(feed.getGptContent())
                .build();
    }
}