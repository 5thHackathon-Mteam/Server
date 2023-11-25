package com.example.hackathon.domain.heart.service;

import static com.example.hackathon.global.error.exception.ErrorCode.FEED_NOT_FOUND;

import com.example.hackathon.domain.feed.repository.FeedRepository;
import com.example.hackathon.domain.heart.domain.Heart;
import com.example.hackathon.domain.heart.dto.HeartCountResponse;
import com.example.hackathon.domain.heart.repository.HeartRepository;
import com.example.hackathon.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HeartService {

    private final HeartRepository heartRepository;
    private final FeedRepository feedRepository;

    public Boolean manageHeart(Long feedId, Long memberId) {

        feedRepository.findById(feedId).orElseThrow(() -> new CustomException(FEED_NOT_FOUND));

        Heart heart = heartRepository.findByMemberIdAndFeedId(memberId, feedId)
                .orElseGet(() -> Heart.builder()
                        .memberId(memberId)
                        .feedId(feedId)
                        .build());

        heart.changeLike();
        heartRepository.save(heart);

        return true;
    }

    public HeartCountResponse heartCount(Long feedId) {
        return new HeartCountResponse(heartRepository.countByFeedId(feedId));
    }
}
