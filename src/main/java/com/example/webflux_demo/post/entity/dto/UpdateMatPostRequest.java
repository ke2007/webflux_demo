package com.example.webflux_demo.post.entity.dto;

import com.example.webflux_demo.post.entity.MatPost;

public record UpdateMatPostRequest(String title, String content, int star, String thumbnailUrl) {


    public MatPost toEntity() {
        return MatPost.builder()
                .title(title)
                .content(content)
                .star(star)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
