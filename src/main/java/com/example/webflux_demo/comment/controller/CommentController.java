package com.example.webflux_demo.comment.controller;


import com.example.webflux_demo.comment.dto.CommentResponse;
import com.example.webflux_demo.comment.dto.SaveCommentRequest;
import com.example.webflux_demo.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places/posts/{post-id}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Mono<ResponseEntity<CommentResponse>> createComment(@RequestBody Mono<SaveCommentRequest> request,
                                                               @PathVariable("post-id") Long postId) {
        Mono<ResponseEntity<CommentResponse>> map = request
                .flatMap(saveCommentRequest -> commentService.save(saveCommentRequest, postId))
                .map(commentResponse -> new ResponseEntity<>(commentResponse, HttpStatus.CREATED));
        return map;
    }

    //TODO patch 와 delete 구현
    @PatchMapping("/{comment-id}")
    public Mono<ResponseEntity<CommentResponse>> updateComment(@Validated @RequestBody Mono<SaveCommentRequest> request,
                                                               @PathVariable("post-id") Long postId,
                                                               @PathVariable("comment-id") Long commentId) {
        return request.flatMap(SaveCommentRequest -> commentService.updateComment(SaveCommentRequest, postId, commentId))
                .map(commentResponse -> new ResponseEntity<>(commentResponse, HttpStatus.OK));
    }

    @DeleteMapping("/{comment-id}")
    public Mono<ResponseEntity<Void>> deleteComment(@PathVariable("comment-id") Long commentId) {

        return commentService.deleteComment(commentId).map(response -> ResponseEntity.noContent().build());
    }
}
