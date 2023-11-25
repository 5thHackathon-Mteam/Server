package com.example.hackathon.domain.heart.service;

import com.example.hackathon.domain.feed.domain.Feed;
import com.example.hackathon.domain.feed.repository.FeedRepository;
import com.example.hackathon.domain.heart.domain.Heart;
import com.example.hackathon.domain.heart.dto.HeartRequest;
import com.example.hackathon.domain.heart.repository.HeartRepository;
import com.example.hackathon.domain.member.domain.Member;
import com.example.hackathon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class HeartService {

    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final FeedRepository feedRepository;

    public void save(HeartRequest heartRequest) throws Exception {
        Member member = memberRepository.findById(heartRequest.memberId())
                .orElseThrow(() -> new NotFoundException("Could not found member id : " + heartRequest.memberId()));

        Feed feed = feedRepository.findById(heartRequest.feedId())
                .orElseThrow(() -> new NotFoundException("Could not found feed id : " + heartRequest.feedId()));

        if (heartRepository.findByMemberIdAndFeedId(heartRequest.memberId(), heartRequest.feedId()).isPresent()) {
            throw new Exception("already exist data by member id : " + heartRequest.memberId() + ", " + "feed id : " + heartRequest.feedId());
        }

        Heart heart = Heart.builder()
                .feedId(heartRequest.feedId())
                .memberId(heartRequest.memberId())
                .build();

        heartRepository.save(heart);
    }

    public void delete(HeartRequest heartRequest) {
        Member member = memberRepository.findById(heartRequest.memberId())
                .orElseThrow(() -> new NotFoundException("Could not found memberId : " + heartRequest.memberId()));

        Feed feed = feedRepository.findById(heartRequest.feedId())
                .orElseThrow(() -> new NotFoundException("Could not found feed id : " + heartRequest.feedId()));

        Heart heart = heartRepository.findByMemberIdAndFeedId(heartRequest.memberId(),
                        heartRequest.feedId())
                .orElseThrow(() -> new NotFoundException("Could not found heart id"));

        heartRepository.delete(heart);
    }
}
