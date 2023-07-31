package com.hackathon.hackathon.service;

import com.hackathon.hackathon.entity.Comment;
import com.hackathon.hackathon.entity.Post;
import com.hackathon.hackathon.entity.User;
import com.hackathon.hackathon.repository.CommentRepository;
import com.hackathon.hackathon.repository.PostRepository;
import com.hackathon.hackathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment createComment(String content, Long postId, Long userId) {
        Comment comment = new Comment();
        comment.setContent(content);

        // postId를 사용하여 Post 엔티티를 가져옴
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            comment.setPost(optionalPost.get());
        } else {
            // Post를 찾지 못한 경우 예외 처리 또는 다른 처리 방법을 구현
            throw new IllegalArgumentException("Post not found with ID: " + postId);
        }

        // userId를 사용하여 User 엔티티를 가져옴
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            comment.setUser(optionalUser.get());
        } else {
            // User를 찾지 못한 경우 예외 처리 또는 다른 처리 방법을 구현
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        comment.setCreatedDateTime(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    //특정 게시글에 속한 모든 댓글들을 조회하는 메서드
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    //특정 댓글 ID에 해당하는 댓글을 조회하는 메서드
    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    //특정 댓글의 내용을 업데이트하는 메서드
    public Comment updateComment(Long commentId, String content) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(content);
            comment.setUpdatedDateTime(LocalDateTime.now());
            return commentRepository.save(comment);
        }
        return null;
    }

    //특정 댓글을 삭제하는 메서드
    public void deleteComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setDeletedDateTime(LocalDateTime.now());
            commentRepository.save(comment);
        }
    }
}

