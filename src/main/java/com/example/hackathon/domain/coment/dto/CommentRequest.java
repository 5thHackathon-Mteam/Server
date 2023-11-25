package com.example.hackathon.domain.coment.dto;

public record CommentRequest(Long memberId, Long parentId, String content) {
}
