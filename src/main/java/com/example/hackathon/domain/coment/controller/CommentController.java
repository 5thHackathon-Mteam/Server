package com.example.hackathon.domain.coment.controller;

import com.example.hackathon.domain.coment.dto.CommentRequest;
import com.example.hackathon.domain.coment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{feedId}")
    public ResponseEntity<Boolean> save(@PathVariable Long feedId, @RequestBody CommentRequest commentRequest) {
        commentService.save(feedId, commentRequest);
        return ResponseEntity.ok()
                .body(true);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Boolean> update(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        commentService.update(commentId, commentRequest);
        return ResponseEntity.ok()
                .body(true);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.ok()
                .body(true);
    }
}
