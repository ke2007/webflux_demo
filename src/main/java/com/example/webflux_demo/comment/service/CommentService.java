package com.example.webflux_demo.comment.service;

import com.example.webflux_demo.comment.dto.CommentResponse;
import com.example.webflux_demo.comment.dto.SaveCommentRequest;
import com.example.webflux_demo.comment.entity.Comment;
import com.example.webflux_demo.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    public Mono<CommentResponse> save(SaveCommentRequest saveCommentRequest) {
        Comment comment = saveCommentRequest.toEntity();

        comment.setMemberId(2L);

        return commentRepository.save(comment).map(CommentResponse::from);
    }
}
