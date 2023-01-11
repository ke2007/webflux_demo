//package com.example.webflux_demo.post.config;
//
//import com.example.webflux_demo.member.Member;
//import com.example.webflux_demo.post.entity.MatPost;
//import io.r2dbc.spi.Row;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.data.convert.ReadingConverter;
//
//import java.time.LocalDateTime;
//
//@ReadingConverter
//public class PostReadConverter implements Converter<Row, MatPost> {
//
//
//    @Override
//    public MatPost convert(Row source) {
//        var member = Member.builder()
//                .id((Long) source.get("id"))
//                .nickname((String) source.get("memberickname"))
//                .build();
//        return MatPost.builder()
//                .id((Long) source.get("id"))
//                .title((String) source.get("title"))
//                .content((String) source.get("content"))
//                .likes((int) source.get("postLikes"))
//                .thumbnailUrl((String) source.get("thumbnail_url"))
//                .createdAt((LocalDateTime) source.get("postCreated_at"))
//                .modifiedAt((LocalDateTime) source.get("postModified_at"))
//                .star((long) source.get("star"))
//                .memberId((Long) source.get("memberId"))
//                .member(member)
//                .build();
//    }
//
//    @Override
//    public <U> Converter<Row, U> andThen(Converter<? super MatPost, ? extends U> after) {
//        return Converter.super.andThen(after);
//    }
//}
