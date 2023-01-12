package com.example.webflux_demo.comment.service;

import com.example.webflux_demo.comment.dto.CommentInfo;
import com.example.webflux_demo.comment.dto.CommentResponse;
import com.example.webflux_demo.comment.dto.SaveCommentRequest;

import com.example.webflux_demo.comment.entity.Comment;
import com.example.webflux_demo.comment.repository.CommentRepository;
import com.example.webflux_demo.member.dto.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Mono<List<CommentInfo>> getComments(Long postId) {
        Mono<List<CommentInfo>> listMono = commentRepository.findPost_CommentWithMember(postId)
                .map(commentSpecificInfo -> {

                    var memberInfo = MemberInfo.builder()
                            .nickname(commentSpecificInfo.nickname())
                            .profileImg(commentSpecificInfo.profileImg())
                            .build();

                    return CommentInfo.builder()
                            .CommentId(commentSpecificInfo.id())
                            .commentContent(commentSpecificInfo.commentContent())
                            .commentCreatedAt(commentSpecificInfo.commentCreatedAt())
                            .memberInfo(memberInfo)
                            .build();
                }).collectList();

        return listMono;
    }

    public Mono<CommentResponse> save(SaveCommentRequest saveCommentRequest, Long postId) {

        Comment postComment = saveCommentRequest.toEntity();
        postComment.setUserId(3L);
        postComment.setFeedId(postId);
        Mono<Comment> save = commentRepository.save(postComment);
        Mono<CommentResponse> map = save.map(CommentResponse::from);
        return map;
    }

    public Mono<CommentResponse> updateComment(SaveCommentRequest saveCommentRequest, Long postId, Long commentId) {
        Comment patchComment = saveCommentRequest.toEntity();

        return commentRepository.findById(commentId).flatMap(comment -> {
            comment.setId(commentId);
            comment.setFeedId(postId);
            comment.setComment_content(patchComment.getComment_content());
            return commentRepository.save(comment);
        }).map(CommentResponse::from);

    }

    public Mono<Void> deleteComment(Long commentId) {
        return commentRepository.deleteById(commentId);
    }

}
