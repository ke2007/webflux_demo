package com.example.webflux_demo.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class PostNotFoundException extends RuntimeException {

    private CustomErrorCode customErrorCode;

    public PostNotFoundException(CustomErrorCode customErrorCode) {
        this.customErrorCode = customErrorCode;
    }
}
