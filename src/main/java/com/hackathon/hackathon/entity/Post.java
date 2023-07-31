package com.hackathon.hackathon.entity;
//다대일 관계이므로 Post 클래스 엔티티를 정의해주어야함
//Post 클래스를 추가하여 Comment 클래스와의 관계를 설정하면 오류가 해결
//Post 클래스에는 comments 필드로 OneToMany 관계를 설정하고, mappedBy 속성을 이용하여 Comment 엔티티의 post 필드와 매핑되도록 함.
//이러면 댓글과 게시글 간의 일대다 관계가 성립합니다.

import com.hackathon.hackathon.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) //댓글이 게시글과 관련이 끊길 경우 자동으로 삭제되도록 설정
    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime createdDateTime;

    private LocalDateTime updatedDateTime;
}

