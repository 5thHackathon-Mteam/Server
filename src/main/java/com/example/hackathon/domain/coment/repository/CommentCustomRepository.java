package com.example.hackathon.domain.coment.repository;

import com.example.hackathon.domain.coment.domain.Comment;
import java.util.Optional;

public interface CommentCustomRepository {
    Optional<Comment> findCommentByIdWithParent(Long id);
}
