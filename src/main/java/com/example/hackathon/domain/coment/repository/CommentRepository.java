package com.example.hackathon.domain.coment.repository;

import com.example.hackathon.domain.coment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {
    List<Comment> findAllByFeedId(Long feedId);
}
