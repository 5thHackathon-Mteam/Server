package com.example.hackathon.domain.coment.service;

import com.example.hackathon.domain.coment.domain.Comment;
import com.example.hackathon.domain.coment.dto.CommentRequest;
import com.example.hackathon.domain.coment.dto.CommentResponse;
import com.example.hackathon.domain.coment.repository.CommentRepository;
import com.example.hackathon.domain.feed.domain.Feed;
import com.example.hackathon.domain.feed.repository.FeedRepository;
import com.example.hackathon.domain.member.domain.Member;
import com.example.hackathon.domain.member.repository.MemberRepository;
import com.example.hackathon.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final FeedRepository feedRepository;

    public Comment save(Long feedId, CommentRequest commentRequest) {

        String username = SecurityUtil.getCurrentUsername();

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Could not found username : " + username));

        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new NotFoundException("Could not found feed id : " + feedId));

        Comment comment = Comment.builder()
                .content(commentRequest.content())
                .writer(member)
                .feed(feed)
                .build();

        if (commentRequest.parentId() != null) {
            Comment parentComment = commentRepository.findById(commentRequest.parentId())
                    .orElseThrow(() -> new NotFoundException("Could not found comment id : " + commentRequest.parentId()));

            comment.updateParent(parentComment);
            parentComment.getChildrenComments().add(comment);
        }

        return commentRepository.save(comment);
    }

    public void delete(Long commentId) {
        Comment comment = commentRepository.findCommentByIdWithParent(commentId)
                .orElseThrow(() -> new NotFoundException("Could not found comment : " + commentId));
        commentRepository.delete(comment);
    }

    public void update(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Could not found comment id : " + commentId));
        comment.updateContent(commentRequest.content());
    }

    public List<CommentResponse> getCommentByFeedId(Long feedId) {

        System.out.println("feedId = " + feedId);

        List<Comment> allByFeedId = commentRepository.findAllByFeedId(feedId);

        return CommentResponse.listOf(allByFeedId);

    }
}
