package com.example.hackathon.domain.feed.dto;

import com.example.hackathon.domain.feed.domain.Category;
import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class FeedResponse {
    private Long id;
    private String gptContent;
    private List<Category> categories;

    @Builder @QueryProjection
    public FeedResponse(Long id, String content, String gptContent, List<Category> categories) {
        this.id = id;
        this.gptContent = gptContent;
        this.categories = categories;
    }
}