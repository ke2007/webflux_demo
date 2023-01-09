package com.example.webflux_demo.post.entity.dto;

import com.example.webflux_demo.post.entity.MatPost;

import java.time.LocalDateTime;

public record MatPostResponse(Long id, String title, String content, int likes, String thumbnailUrl,
                              LocalDateTime createdAt, LocalDateTime modifiedAt, long star) {

    public static MatPostResponse from(MatPost matPost) {
        return new MatPostResponse(
                matPost.getId(),
                matPost.getTitle(),
                matPost.getContent(),
                matPost.getLikes(),
                matPost.getThumbnailUrl(),
                matPost.getCreatedAt(),
                matPost.getModifiedAt(),
                matPost.getStar());
    }
}
