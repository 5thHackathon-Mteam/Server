package com.example.hackathon.domain.heart.repository;

import com.example.hackathon.domain.heart.domain.QHeart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HeartRepositoryImpl implements HeartRepositoryCustom {

    JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countByFeedId(Long feedId) {
        return jpaQueryFactory
            .selectFrom(QHeart.heart)
            .where(QHeart.heart.feedId.eq(feedId), QHeart.heart.isLike.eq(true))
            .fetchCount();
    }
}
