package com.example.hackathon.domain.feed.repository;

import com.example.hackathon.domain.feed.domain.FeedImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedImageRepository extends JpaRepository<FeedImage, Long> {
    List<FeedImage> findAllByFeedId(Long feedId);
}
