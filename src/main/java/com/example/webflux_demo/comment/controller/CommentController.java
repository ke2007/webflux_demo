package com.example.webflux_demo.comment.controller;


import com.example.webflux_demo.comment.dto.CommentResponse;
import com.example.webflux_demo.comment.dto.SaveCommentRequest;
import com.example.webflux_demo.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places/posts/{post-id}/comments")
public class CommentController {

    private CommentService commentService;

    @PostMapping
    public Mono<ResponseEntity<CommentResponse>> createComment(@RequestBody Mono<SaveCommentRequest> request) {

        Mono<ResponseEntity<CommentResponse>> map = request.flatMap(commentService::save).map(ResponseEntity::ok);
        return map;

    }
}
