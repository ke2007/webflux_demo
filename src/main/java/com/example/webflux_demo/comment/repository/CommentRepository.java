package com.example.webflux_demo.comment.repository;

import com.example.webflux_demo.comment.entity.Comment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CommentRepository extends ReactiveCrudRepository<Comment, Long> {


}
