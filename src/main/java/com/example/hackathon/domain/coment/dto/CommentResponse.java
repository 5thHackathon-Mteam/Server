package com.example.hackathon.domain.coment.dto;

import com.example.hackathon.domain.coment.domain.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CommentResponse(
        Long id,
        String content,
        Long writerNickname,
        List<CommentResponse> commentList,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {
    public static CommentResponse from(Comment comment) {
        List<CommentResponse> childComments = comment.getChildrenComments() != null ?
                comment.getChildrenComments().stream()
                        .map(CommentResponse::from)
                        .collect(Collectors.toList()) : null;

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getWriter().getId(),
                childComments,
                comment.getCreatedDate(),
                comment.getLastModifiedDate()
        );
    }
}
