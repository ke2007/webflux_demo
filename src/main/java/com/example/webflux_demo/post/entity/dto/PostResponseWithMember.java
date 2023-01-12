package com.example.webflux_demo.post.entity.dto;

import com.example.webflux_demo.comment.dto.CommentInfo;
import com.example.webflux_demo.member.dto.MemberInfo;
import lombok.Builder;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostResponseWithMember(Long id, String title,
                                     String content, int likes,
                                     String thumbnailUrl, LocalDateTime createdAt, LocalDateTime modifiedAt, int star,
                                     MemberInfo memberInfo) {
}
