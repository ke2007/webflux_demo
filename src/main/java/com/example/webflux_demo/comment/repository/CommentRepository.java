package com.example.webflux_demo.comment.repository;



import com.example.webflux_demo.comment.dto.CommentSpecificInfo;
import com.example.webflux_demo.comment.entity.Comment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveCrudRepository<Comment, Long> {


    @Query("""
            SELECT
            pc.id,
            pc.feed_id,
            pc.user_id,
            pc.comment_content,
            pc.comment_created_at,
            m.nickname,
            m.profile_img
            FROM post_comment pc
            INNER JOIN member m
            ON pc.user_id = m.id
            where pc.feed_id = :postId
           """)
    Flux<CommentSpecificInfo> findPost_CommentWithMember(Long PostId);

}
