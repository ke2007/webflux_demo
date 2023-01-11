package com.example.webflux_demo.exception;


public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("게시물 번호 : " + id +" 번 게시물을 찾을 수가 없습니다 !! ");
    }
}
