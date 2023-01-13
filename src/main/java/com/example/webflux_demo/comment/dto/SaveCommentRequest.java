package com.example.webflux_demo.comment.dto;



import com.example.webflux_demo.comment.entity.Comment;
import jakarta.validation.constraints.NotBlank;


public record SaveCommentRequest(@NotBlank(message = " 댓글내용은 비어있을 수 없습니다") String content) {

    public Comment toEntity() {
        return Comment.builder()
                .comment_content(content)
                .build();
    }
}
