package com.example.webflux_demo.post.service;

import com.example.webflux_demo.comment.dto.CommentInfo;
import com.example.webflux_demo.comment.dto.MultiResponseDto;
import com.example.webflux_demo.comment.service.CommentService;
import com.example.webflux_demo.member.dto.MemberInfo;
import com.example.webflux_demo.post.entity.MatPost;
import com.example.webflux_demo.post.entity.dto.MatPostResponse;
import com.example.webflux_demo.post.entity.dto.PostResponseWithMember;
import com.example.webflux_demo.post.entity.dto.SaveMatPostRequest;
import com.example.webflux_demo.post.entity.dto.UpdateMatPostRequest;
import com.example.webflux_demo.post.repository.MatPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatPostService {

    private final CommentService commentService;
    private final MatPostRepository matPostRepository;


    public Flux<MatPostResponse> getAll(int page, int size) {

        Flux<MatPostResponse> map = matPostRepository.findAll()
                .skip(page * size)
                .take(size)
                .map(MatPostResponse::from);
        return map;
    }

    @Transactional(readOnly = true)
    public Mono<MatPostResponse> getOne(Long matPostId) {

        Mono<MatPostResponse> responseFlux = matPostRepository.findById(matPostId)
                .map(MatPostResponse::from);
        return responseFlux;
    }

    @Transactional(readOnly = true)
    public Flux<MatPostResponse> findPostByKeyword(String keyword) {

        Flux<MatPostResponse> listMono = matPostRepository.searchMatPostByKeyword(keyword)
                .map(MatPostResponse::from);
        return listMono;
    }
    @Transactional(readOnly = true)
    public Flux<MatPostResponse> findPostByContentKeyword(String keyword) {

        Flux<MatPostResponse> listMono = matPostRepository.searchMatPostByContentKeyword(keyword)
                .map(MatPostResponse::from);
        return listMono;
    }

    @Transactional
    public Mono<MatPostResponse> save(SaveMatPostRequest request) {

        MatPost matPost = request.toEntity();
        //TODO 멤버 완성되면 멤버ID 토큰에서 뺴오는작업 해야함.
        matPost.setMemberId(2L);

        Mono<MatPost> save = matPostRepository.save(matPost);
        Mono<MatPostResponse> map = save.map(MatPostResponse::from);

        return map;
    }

    @Transactional
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Mono<MatPostResponse> update(UpdateMatPostRequest updateMatPostRequest, Long postId) {
        MatPost matPost = updateMatPostRequest.toEntity();

        Mono<MatPostResponse> map = matPostRepository.findById(postId)
                .flatMap(post -> matPostRepository.save(post.settingPost(post, matPost)))
                .map(MatPostResponse::from);

        return map;
    }


    @Transactional
    public Mono<Void> delete(Long postId) {

        Mono<Void> mono = matPostRepository.findById(postId)
                .flatMap(matPostRepository::delete)
                .switchIfEmpty(matPostRepository.PostDeleteWithCommentsLikes(postId));
        return mono;
    }
    @Transactional(readOnly = true)
    public Mono<MultiResponseDto> getPost(Long postId) {
        Mono<MultiResponseDto> map = matPostRepository.findPostWithMemberInfo(postId)
                .publishOn(Schedulers.boundedElastic())
                .map(result -> {

                    var member = MemberInfo.builder()
                            .nickname(result.nickname())
                            .profileImg(result.profileImg())
                            .build();

                    var comments = commentService.getComments(postId).block();

                    PostResponseWithMember build = PostResponseWithMember.builder()
                            .id(result.id())
                            .title(result.title())
                            .content(result.content())
                            .likes(result.likes())
                            .thumbnailUrl(result.thumbnailUrl())
                            .star(result.star())
                            .createdAt(result.createdAt())
                            .modifiedAt(result.modifiedAt())
                            .memberInfo(member)
                            .build();

                    MultiResponseDto multiResponseDto = new MultiResponseDto(build, comments);
                    return multiResponseDto;
                });

        return map;
    }

}
