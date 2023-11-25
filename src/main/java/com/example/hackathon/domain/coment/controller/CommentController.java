package com.example.hackathon.domain.coment.controller;

import com.example.hackathon.domain.coment.dto.CommentRequest;
import com.example.hackathon.domain.coment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
