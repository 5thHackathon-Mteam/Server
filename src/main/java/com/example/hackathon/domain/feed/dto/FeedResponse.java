package com.example.hackathon.domain.feed.dto;

import com.example.hackathon.domain.feed.domain.Feed;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter @NoArgsConstructor
public class FeedResponse {
    private Long id;
    private String gptContent;
    private String frameColor;
    private LocalDate date;

    @Builder @QueryProjection
    public FeedResponse(Long id, String gptContent, String frameColor, LocalDate date) {
        this.id = id;
        this.gptContent = gptContent;
        this.frameColor = frameColor;
        this.date = date;
    }

    public static FeedResponse from(Feed feed) {
        return FeedResponse.builder()
                .id(feed.getId())
                .gptContent(feed.getGptContent())
                .frameColor(feed.getFrameColor())
                .date(feed.getDate())
                .build();
    }
}