package com.example.hackathon.domain.feed.dto;

import com.example.hackathon.domain.feed.domain.Category;
import com.example.hackathon.domain.feed.domain.Feed;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class FeedResponse {
    private Long id;
    private String content;
    private String gptContent;
    private Category category;

    @Builder @QueryProjection
    public FeedResponse(Long id, String content, String gptContent, Category category) {
        this.id = id;
        this.content = content;
        this.gptContent = gptContent;
        this.category = category;
    }

    public static FeedResponse from(Feed feed) {
        return FeedResponse.builder()
                .id(feed.getId())
                .content(feed.getContent())
                .gptContent(feed.getGptContent())
                .category(feed.getCategory())
                .build();
    }
}