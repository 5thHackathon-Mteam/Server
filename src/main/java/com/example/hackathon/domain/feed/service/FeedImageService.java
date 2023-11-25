package com.example.hackathon.domain.feed.service;

import com.example.hackathon.domain.feed.domain.Feed;
import com.example.hackathon.domain.feed.domain.FeedImage;
import com.example.hackathon.domain.feed.repository.FeedImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedImageService {
    private final FeedImageRepository feedImageRepository;

    public void from(Feed feed, String imageUrl) {
        FeedImage feedImage = FeedImage.builder()
                .feed(feed)
                .imageUrl(imageUrl)
                .build();
        feedImageRepository.save(feedImage);
    }
}
