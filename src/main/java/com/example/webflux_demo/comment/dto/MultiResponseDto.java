package com.example.webflux_demo.comment.dto;

import com.example.webflux_demo.post.entity.dto.PostResponseWithMember;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MultiResponseDto {

    @JsonProperty
    private PostResponseWithMember postInfo;
    @JsonProperty
    private List<CommentInfo> comments;

    public MultiResponseDto(PostResponseWithMember response, List<CommentInfo> comments) {
        this.postInfo = response;
        this.comments = comments;
    }
}
