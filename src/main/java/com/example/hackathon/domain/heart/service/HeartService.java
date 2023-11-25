package com.example.hackathon.domain.heart.service;

import static com.example.hackathon.global.error.exception.ErrorCode.FEED_NOT_FOUND;

import com.example.hackathon.domain.feed.repository.FeedRepository;
import com.example.hackathon.domain.heart.domain.Heart;
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

    public void manageHeart(Long feedId) {

        feedRepository.findById(feedId).orElseThrow(() -> new CustomException(FEED_NOT_FOUND));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Heart heart = heartRepository.findByUsernameAndFeedId(email, feedId)
                .orElseGet(() -> Heart.builder()
                        .username(email)
                        .feedId(feedId)
                        .build());

        heart.changeLike(false);
        heartRepository.save(heart);
    }
}
