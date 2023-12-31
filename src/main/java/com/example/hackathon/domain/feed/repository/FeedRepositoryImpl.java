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
                        feed.gptContent,
                        feed.frameColor,
                        feed.date
                ))
                .from(feed)
                .where(ltCursorId(cursorId), feed.isDeleted.eq(false))
                .orderBy(feed.createdDate.desc())
                .limit(pageSize)
                .fetch();
    }

    @Override
    public List<FeedResponse> findPageByCursorIdAndMemberId(Long cursorId, int pageSize, Long memberId) {
        return jpaQueryFactory
                .select(new QFeedResponse(
                        feed.id,
                        feed.gptContent,
                        feed.frameColor,
                        feed.date
                ))
                .from(feed)
                .where(ltCursorId(cursorId), feed.isDeleted.eq(false), feed.memberId.eq(memberId))
                .orderBy(feed.createdDate.desc())
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        return cursorId != null ? feed.id.lt(cursorId) : null;
    }
}
