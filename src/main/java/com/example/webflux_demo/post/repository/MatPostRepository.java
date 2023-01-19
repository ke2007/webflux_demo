package com.example.webflux_demo.post.repository;


import com.example.webflux_demo.member.dto.PostUserSpecificInfo;
import com.example.webflux_demo.post.entity.MatPost;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface MatPostRepository extends ReactiveCrudRepository<MatPost, Long> {

    @Query("""
            SELECT *
            FROM mat_post p
            WHERE p.title
            LIKE CONCAT('%', :keyword, '%')
            """)
    Flux<MatPost> searchMatPostByKeyword(String keyword);

    @Query("""
            SELECT *
            FROM mat_post p
            WHERE p.content
            LIKE CONCAT('%', :keyword, '%')
            """)
    Flux<MatPost> searchMatPostByContentKeyword(String keyword);


    //TODO 쿼리 수정
    @Query("""
            SELECT
            p.id,
            p.title,
            p.content,
            p.likes,
            p.thumbnail_url,
            p.star,
            p.created_at,
            p.modified_at,
            m.nickname,
            m.profile_img
            FROM mat_post p
            INNER JOIN member m
            ON p.member_id = m.id
            where p.id = :postId
            """)
    Mono<PostUserSpecificInfo> findPostWithMemberInfo(Long postId);

    @Query("""
           DELETE
           FROM pc,lc,pl
           USING post_comment pc
           LEFT JOIN likes_count lc
           ON pc.post_id = lc.likes_post_id
           LEFT JOIN post_likes pl
           ON pl.post_id = lc.likes_post_id
           where pc.post_id = :postId
           """)
    Mono<Void> PostDeleteWithCommentsLikes(Long postId);
}
