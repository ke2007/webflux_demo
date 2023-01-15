package com.example.webflux_demo.post.entity;




import com.example.webflux_demo.comment.entity.Comment;
import com.example.webflux_demo.member.dto.MemberInfo;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;


import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "mat_post")
public class MatPost {

    @Id
    private Long id;

    private String title;

    private String content;

    private int likes;

    private String thumbnailUrl;

    private int star;

    private Long memberId;

    @Transient
    private MemberInfo member;

    @Transient
    private List<Comment> comments;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public void settingLikes(int count) {
        this.likes = count;
    }
    public MatPost settingPost(MatPost post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.thumbnailUrl = post.getThumbnailUrl();
        this.star = post.getStar();
        return post;
    }

}
