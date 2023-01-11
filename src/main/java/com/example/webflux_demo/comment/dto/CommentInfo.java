package com.example.webflux_demo.comment.dto;

import com.example.webflux_demo.member.dto.MemberInfo;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentInfo(String commentContent, LocalDateTime commentCreatedAt, MemberInfo memberInfo) {
}
