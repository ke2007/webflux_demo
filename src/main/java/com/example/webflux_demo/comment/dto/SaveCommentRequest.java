package com.example.webflux_demo.comment.dto;



import com.example.webflux_demo.comment.entity.Comment;

public record SaveCommentRequest(String content) {

    public Comment toEntity() {
        return Comment.builder()
                .comment_content(content)
                .build();
    }
}
