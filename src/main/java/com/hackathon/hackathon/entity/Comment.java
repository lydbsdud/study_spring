package com.hackathon.hackathon.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"post", "user"})
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId; // 댓글 번호

    @ManyToOne //다대일 관계
    @JoinColumn(name = "post_id", nullable = false) //외래키로 사용될 컬럼명을 지정
    private Post post; // 게시글 번호 외래키

    @ManyToOne //다대일 관계
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성자 ID 외래키

    @Column(nullable = false)
    private String content; // 댓글 내용

    private LocalDateTime createdDateTime; // 댓글 작성 일시

    private LocalDateTime updatedDateTime; // 댓글 수정 일시

    private LocalDateTime deletedDateTime; // 댓글 삭제 일시
}

