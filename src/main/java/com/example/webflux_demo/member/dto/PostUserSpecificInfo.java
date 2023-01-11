package com.example.webflux_demo.member.dto;

import lombok.Builder;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Builder
public record PostUserSpecificInfo(Long id , String title,
                                   String content, int likes,
                                   String thumbnailUrl, int star,
                                   LocalDateTime createdAt, LocalDateTime modifiedAt, String nickname,String profileImg) {
}
