package com.example.webflux_demo.post.service;

import com.example.webflux_demo.member.dto.PostUserSpecificInfo;
import com.example.webflux_demo.post.entity.MatPost;
import com.example.webflux_demo.post.entity.dto.MatPostResponse;
import com.example.webflux_demo.post.entity.dto.SaveMatPostRequest;
import com.example.webflux_demo.post.entity.dto.UpdateMatPostRequest;
import com.example.webflux_demo.post.repository.CustomRepository;
import com.example.webflux_demo.post.repository.MatPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatPostService {

    private final MatPostRepository matPostRepository;
    private final CustomRepository customRepository;

    public Flux<MatPostResponse> getAll(){

        Flux<MatPostResponse> responseFlux = matPostRepository.findAll()
                .map(MatPostResponse::from);
        return responseFlux;
    }

    public Mono<MatPostResponse> getOne(Long matPostId) {

        Mono<MatPostResponse> responseFlux = matPostRepository.findById(matPostId)
                .map(MatPostResponse::from);
        return responseFlux;
    }

    public Flux<MatPostResponse> findPostByKeyword(String keyword) {

        Flux<MatPostResponse> listMono = matPostRepository.searchMatPostByKeyword(keyword)
                .map(MatPostResponse::from);
        return listMono;
    }

    public Mono<MatPostResponse> save(SaveMatPostRequest request) {

        MatPost matPost = request.toEntity();
        matPost.setMemberId(2L);

        Mono<MatPost> save = matPostRepository.save(matPost);
        Mono<MatPostResponse> map = save.map(MatPostResponse::from);

        return map;
    }

    public Mono<MatPostResponse> update(UpdateMatPostRequest updateMatPostRequest, Long postId) {
        MatPost matPost = updateMatPostRequest.toEntity();

        Mono<MatPostResponse> map = matPostRepository.findById(postId).flatMap(post -> {
            post.setTitle(matPost.getTitle());
            post.setContent(matPost.getContent());
            post.setThumbnailUrl(matPost.getThumbnailUrl());
            post.setStar(matPost.getStar());
            MatPostResponse.from(post);
            return matPostRepository.save(post);
        }).map(MatPostResponse::from);

        return map;
    }

    public Mono<Void> delete(Long postId) {

        Mono<Void> mono = matPostRepository.findById(postId)
                .flatMap(matPostRepository::delete);
        return mono;
    }

    public Mono<PostUserSpecificInfo> getPost(Long postId) {

        return matPostRepository.findPostWithMemberInfo(postId);
    }
}
