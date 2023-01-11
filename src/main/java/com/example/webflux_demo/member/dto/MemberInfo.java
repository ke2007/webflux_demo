package com.example.webflux_demo.member.dto;

import com.example.webflux_demo.member.Member;
import lombok.Builder;

@Builder
public record MemberInfo(String nickname, String profileImg) {

    public static MemberInfo toEntity(Member member) {
        return new MemberInfo(
                member.getNickname(),
                member.getProfileImg()
        );
    }
}
