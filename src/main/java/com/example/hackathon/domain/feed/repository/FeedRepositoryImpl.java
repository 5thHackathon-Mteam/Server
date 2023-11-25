package com.example.hackathon.domain.feed.repository;

import com.example.hackathon.domain.feed.dto.FeedResponse;
import com.example.hackathon.domain.feed.dto.QFeedResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.hackathon.domain.feed.domain.QFeed.feed;

@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FeedResponse> findPageByCursorId(Long cursorId, int pageSize) {
        return jpaQueryFactory
                .select(new QFeedResponse(
                        feed.id,
                        feed.content,
                        feed.gptContent,
                        feed.category
                ))
                .from(feed)
                .where(ltCursorId(cursorId), feed.isDeleted.eq(false))
                .orderBy(feed.createdDate.desc())
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        return cursorId != null ? feed.id.lt(cursorId) : null;
    }
}
