package com.example.webflux_demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
class RestExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    Mono<ResponseEntity<CustomErrorResponse>> postNotFound(PostNotFoundException exception) {

        log.debug("handling exception:" + exception);

        return Mono.just(Objects.requireNonNull(ResponseEntity.status(HttpStatus.NO_CONTENT).body(CustomErrorResponse.error(exception)).getBody()));
    }
}
