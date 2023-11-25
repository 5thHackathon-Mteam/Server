package com.example.hackathon.domain.heart.repository;

import com.example.hackathon.domain.heart.domain.Heart;

import java.util.Optional;

public interface HeartRepositoryCustom {
    Heart findByMemberIdAndFeedId(Long memberId, Long feedId);
    Long countByFeedId(Long feedId);
}
