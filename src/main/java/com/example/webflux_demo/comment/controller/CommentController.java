package com.example.webflux_demo.comment.controller;


import com.example.webflux_demo.comment.dto.CommentInfo;
import com.example.webflux_demo.comment.dto.CommentResponse;
import com.example.webflux_demo.comment.dto.SaveCommentRequest;
import com.example.webflux_demo.comment.service.CommentService;
import com.example.webflux_demo.exception.CustomErrorCode;
import com.example.webflux_demo.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places/posts/{post-id}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Mono<ResponseEntity<CommentResponse>> createComment(@Validated @RequestBody Mono<SaveCommentRequest> request,
                                                               @PathVariable("post-id") Long postId) {
        Mono<ResponseEntity<CommentResponse>> map = request
                .flatMap(saveCommentRequest -> commentService.save(saveCommentRequest, postId))
                .map(commentResponse -> new ResponseEntity<>(commentResponse, HttpStatus.CREATED));
        return map;
    }


    @PatchMapping("/{comment-id}")
    public Mono<ResponseEntity<CommentResponse>> updateComment(@Validated @RequestBody Mono<SaveCommentRequest> request,
                                                               @PathVariable("post-id") Long postId,
                                                               @PathVariable("comment-id") Long commentId) {
        return request.flatMap(SaveCommentRequest -> commentService.updateComment(SaveCommentRequest, postId, commentId))
                .map(commentResponse -> new ResponseEntity<>(commentResponse, HttpStatus.OK));
    }

    @DeleteMapping("/{comment-id}")
    public Mono<ResponseEntity<Void>> deleteComment(@PathVariable("comment-id") Long commentId) {

        return commentService.deleteComment(commentId)
                .map(response -> ResponseEntity.noContent().<Void>build())
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }

    //TODO 특정 게시글의 댓글들 조회 ( 필요시 반영 )
    @GetMapping("/comment-reload")
    public Mono<ResponseEntity<List<CommentInfo>>> reloadComments(@PathVariable("post-id")Long postId) {
        return commentService.getComments(postId)
                .map(commentInfos -> new ResponseEntity<>(commentInfos,HttpStatus.OK));
    }

}
