package com.hackathon.hackathon.repository;

//데이터베이스와의 통신을 추상화하고, 엔티티들을 쿼리와 연결하여 데이터 조작을 쉽게 할 수 있도록 도와줌.

import com.hackathon.hackathon.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
}

