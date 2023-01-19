package com.example.webflux_demo.like.service;

import com.example.webflux_demo.like.dto.LikeRequest;
import com.example.webflux_demo.like.entity.Likes;
import com.example.webflux_demo.like.repository.LikesRepository;
import com.example.webflux_demo.post.entity.MatPost;
import com.example.webflux_demo.post.repository.MatPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikesRepository likesRepository;

    @Transactional
    public Mono<Void> increaseLikes(LikeRequest likeRequest, Long postId, Long memberId) {

        Likes likes = likeRequest.toEntity();
        likes.settingLikes(memberId, postId, likeRequest.likesCheck());
        return likesRepository.save(likes)
                .then(likesRepository.increasePostLikesCount(postId))
                .then(likesRepository.getLikesCount(postId)
                        .flatMap(countInteger -> likesRepository.updatePostLikes(postId, countInteger.intValue()))
                        );

    }
    @Transactional
    public Mono<Void> decreaseLikes(Long postId, Long memberId) {
        //TODO 멤버 검증로직 들어가야함 .

        return likesRepository.findLikes(postId, memberId).flatMap(likesRepository::delete)
                .then(likesRepository.decreasePostLikesCount(postId)).then(likesRepository.getLikesCount(postId)
                        .flatMap(countInteger -> likesRepository.updatePostLikes(postId, countInteger.intValue()))
                );

    }

}