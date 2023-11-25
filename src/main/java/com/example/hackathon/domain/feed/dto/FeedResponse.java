package com.example.hackathon.domain.feed.dto;

import com.example.hackathon.domain.feed.domain.Feed;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class FeedResponse {
    private Long id;
    private String gptContent;
    private String frameColor;
    private LocalDate date;
    private FeedImageResponse feedImageResponse1;
    private FeedImageResponse feedImageResponse2;
    private FeedImageResponse feedImageResponse3;
    private FeedImageResponse feedImageResponse4;

    @Builder @QueryProjection
    public FeedResponse(Long id, String gptContent, String frameColor, LocalDate date) {
        this.id = id;
        this.gptContent = gptContent;
        this.frameColor = frameColor;
        this.date = date;
    }


    @Builder
    public FeedResponse(Long id, String gptContent, String frameColor, LocalDate date, FeedImageResponse feedImageResponse1, FeedImageResponse feedImageResponse2, FeedImageResponse feedImageResponse3, FeedImageResponse feedImageResponse4) {
        this.id = id;
        this.gptContent = gptContent;
        this.frameColor = frameColor;
        this.date = date;
        this.feedImageResponse1 = feedImageResponse1;
        this.feedImageResponse2 = feedImageResponse2;
        this.feedImageResponse3 = feedImageResponse3;
        this.feedImageResponse4 = feedImageResponse4;
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