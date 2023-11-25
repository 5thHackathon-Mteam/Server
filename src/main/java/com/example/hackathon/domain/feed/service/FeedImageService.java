package com.example.hackathon.domain.feed.service;

import com.example.hackathon.domain.feed.domain.FeedImage;
import com.example.hackathon.domain.feed.repository.FeedImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedImageService {
    private final FeedImageRepository feedImageRepository;

    public FeedImage from(String imageUrl) {
        FeedImage feedImage = FeedImage.builder()
                .imageUrl(imageUrl)
                .build();
        feedImageRepository.save(feedImage);
        return feedImage;
    }
}
