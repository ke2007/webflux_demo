package com.example.webflux_demo.post.entity.dto;

import com.example.webflux_demo.post.entity.MatPost;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record SaveMatPostRequest(@NotBlank(message = "제목은 공백일 수 없습니다 !") String title,@NotBlank(message = "내용은 공백일 수 없습니다 !")String content,
                                 String thumbnailUrl, int star) {


    public MatPost toEntity() {
        return MatPost.builder()
                .title(title)
                .content(content)
                .thumbnailUrl(thumbnailUrl)
                .star(star)
                .build();
    }
}
