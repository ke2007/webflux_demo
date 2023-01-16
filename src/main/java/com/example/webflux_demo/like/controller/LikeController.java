package com.example.webflux_demo.like.controller;


import com.example.webflux_demo.like.dto.LikeRequest;
import com.example.webflux_demo.like.entity.Likes;
import com.example.webflux_demo.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/places/posts/{post-id}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public Mono<Void> createLikes(@RequestBody LikeRequest likeRequest,
                                   @PathVariable("post-id") Long postId) {
        //TODO member 구현시 member 정보 들어가야함
        //TODO likes post 에서 불리언처리
        Long memberId = 4L;
        return likeService.increaseLikes(likeRequest, postId, memberId);
    }

    @DeleteMapping
    public Mono<Void> deleteLikes(@PathVariable("post-id") Long postId) {
        Long memberId = 4L;
        return likeService.decreaseLikes(postId,memberId);
    }
}
