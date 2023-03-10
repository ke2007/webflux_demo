package com.example.webflux_demo.comment.dto;


import com.example.webflux_demo.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentResponse(Long id, String content, Long memberId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getCommentContent(),
                comment.getUserId(),
                comment.getCommentCreatedAt(),
                comment.getCommentModifiedAt()
        );
    }
}
