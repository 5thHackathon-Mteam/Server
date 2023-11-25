package com.example.hackathon.domain.feed.repository;

import com.example.hackathon.domain.feed.dto.FeedResponse;

import java.util.List;

public interface FeedRepositoryCustom {
    List<FeedResponse> findPageByCursorId(Long cursorId, int pageSize);
}
