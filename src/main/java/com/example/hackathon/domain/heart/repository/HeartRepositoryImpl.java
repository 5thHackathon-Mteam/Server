package com.example.hackathon.domain.heart.repository;

import com.example.hackathon.domain.heart.domain.Heart;
import com.example.hackathon.domain.heart.domain.QHeart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class HeartRepositoryImpl implements HeartRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Heart findByMemberIdAndFeedId(Long memberId, Long feedId) {
        return jpaQueryFactory
            .selectFrom(QHeart.heart)
            .where(QHeart.heart.memberId.eq(memberId), QHeart.heart.feedId.eq(feedId))
            .fetchOne();
    }

    @Override
    public Long countByFeedId(Long feedId) {
        return jpaQueryFactory
            .selectFrom(QHeart.heart)
            .where(QHeart.heart.feedId.eq(feedId), QHeart.heart.isLike.eq(true))
            .fetchCount();
    }
}
