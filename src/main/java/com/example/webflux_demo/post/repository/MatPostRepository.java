package com.example.webflux_demo.post.repository;


import com.example.webflux_demo.member.dto.PostUserSpecificInfo;
import com.example.webflux_demo.post.entity.MatPost;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MatPostRepository extends ReactiveCrudRepository<MatPost, Long> {

    @Query("""
            SELECT * FROM mat_post p WHERE p.title LIKE CONCAT('%', :keyword, '%') OR p.content LIKE CONCAT('%', :keyword, '%')
            """)
    Flux<MatPost> searchMatPostByKeyword(String keyword);


    @Query("""
            SELECT * FROM mat_post p WHERE p.member_id
""")
    Mono<MatPost> findMatPostWithMember(Long memberId);

    @Query("SELECT p.id,p.title, p.content, p.likes, p.thumbnail_url, p.star, p.created_at, p.modified_at, m.nickname, m.profile_img FROM mat_post p INNER JOIN member m ON p.member_id = m.id where p.id = :postId ")
    Mono<PostUserSpecificInfo> findPostWithMemberInfo(Long postId);

}
