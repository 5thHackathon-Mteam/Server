package com.example.hackathon.domain.feed.repository;

import com.example.hackathon.domain.feed.domain.FeedImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedImageRepository extends JpaRepository<FeedImage, Long> {
}
