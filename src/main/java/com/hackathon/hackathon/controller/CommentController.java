package com.hackathon.hackathon.controller;
//컨트롤러는 클라이언트로부터 요청을 받아 해당 요청에 맞는 서비스를 호출하여 비즈니스 로직을 수행한 뒤, 결과를 클라이언트에 반환하는 역할

import com.hackathon.hackathon.dto.CommentRequest;
import com.hackathon.hackathon.entity.Comment;
import com.hackathon.hackathon.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest commentRequest) {
        Comment comment = commentService.createComment(commentRequest.getContent(), commentRequest.getPostId(), commentRequest.getUserId());
        if (comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        return comment.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        Comment updatedComment = commentService.updateComment(commentId, commentRequest.getContent());
        if (updatedComment != null) {
            return new ResponseEntity<>(updatedComment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

