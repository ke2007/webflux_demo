package com.example.webflux_demo.post.entity.dto;

import com.example.webflux_demo.post.entity.MatPost;

public record SaveMatPostRequest(Long postId,String title, String content, Long star, String thumbnailUrl) {


    public MatPost toEntity() {
        return MatPost.builder()
                .id(postId)
                .title(title)
                .content(content)
                .star(star)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
