package com.example.webflux_demo.comment.entity;

import com.example.webflux_demo.member.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post_comment")
public class Comment {

    @Id
    private Long id;

    private String commentContent;

    private Long userId;

    private Long postId;

    @Transient
    private Member member;

    @CreatedDate
    private LocalDateTime commentCreatedAt;

    @LastModifiedDate
    private LocalDateTime commentModifiedAt;

    public void updatePostId(Long postId) {
        this.postId = postId;
    }

    public void patchComment(Long id, Long postId, String commentContent) {
        this.id = id;
        this.postId = postId;
        this.commentContent = commentContent;
    }
}
