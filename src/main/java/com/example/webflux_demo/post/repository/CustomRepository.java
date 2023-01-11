package com.example.webflux_demo.post.repository;

import com.example.webflux_demo.comment.entity.Comment;
import com.example.webflux_demo.member.dto.PostUserSpecificInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface CustomRepository {
    public Flux<Comment> findPostWithComments();
}
