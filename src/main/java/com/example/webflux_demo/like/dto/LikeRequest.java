package com.example.webflux_demo.like.dto;

import com.example.webflux_demo.like.entity.Likes;

public record LikeRequest(int likesCheck) {

    public Likes toEntity() {
        return Likes.builder()
                .likesCheck(likesCheck)
                .build();
    }
}
