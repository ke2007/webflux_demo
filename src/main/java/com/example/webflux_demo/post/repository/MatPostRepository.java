package com.example.webflux_demo.post.repository;


import com.example.webflux_demo.post.entity.MatPost;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MatPostRepository extends ReactiveCrudRepository<MatPost,Long> {

    @Query("""
           SELECT * FROM mat_post p WHERE p.title LIKE CONCAT('%', :keyword, '%') OR p.content LIKE CONCAT('%', :keyword, '%')
           """)
    Flux<MatPost> searchMatPostByKeyword(String keyword);

}
