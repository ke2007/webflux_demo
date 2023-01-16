package com.example.webflux_demo.like.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

@Table("post_likes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Likes {

    @Id
    private Long id;

    private Long postId;

    private Long placeId;

    private Long userId;

    private int likesCheck;

    public void settingLikes(Long memberId, Long postId, int likesCheck) {
        this.userId = memberId;
        this.postId = postId;
        this.likesCheck = likesCheck;
    }
}
