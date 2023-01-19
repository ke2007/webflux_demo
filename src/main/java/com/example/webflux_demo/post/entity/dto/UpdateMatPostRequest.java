package com.example.webflux_demo.post.entity.dto;

import com.example.webflux_demo.post.entity.MatPost;
import lombok.Builder;

@Builder
public record UpdateMatPostRequest(String title, String content, String thumbnailUrl,int star) {


    public MatPost toEntity() {
        return MatPost.builder()
                .title(title)
                .content(content)
                .thumbnailUrl(thumbnailUrl)
                .star(star)
                .build();
    }
}
