package com.example.webflux_demo.post.controller;


import com.example.webflux_demo.exception.PostNotFoundException;
import com.example.webflux_demo.member.dto.PostUserSpecificInfo;
import com.example.webflux_demo.post.entity.dto.MatPostResponse;
import com.example.webflux_demo.post.entity.dto.SaveMatPostRequest;
import com.example.webflux_demo.post.entity.dto.UpdateMatPostRequest;
import com.example.webflux_demo.post.service.MatPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/places/posts")
@RequiredArgsConstructor
public class MatPostController {
    private final MatPostService matPostService;

    @GetMapping
    public Mono<ResponseEntity<List<MatPostResponse>>> getAllMatPosts() {

        Mono<ResponseEntity<List<MatPostResponse>>> responseEntityMono =
                matPostService.getAll().collectList()
                .map(ResponseEntity::ok);

        return responseEntityMono;
    }

    @GetMapping("/{post-id}")
    public Mono<ResponseEntity<MatPostResponse>> getOneMatPost(@PathVariable("post-id") Long matPostId) {

        Mono<ResponseEntity<MatPostResponse>> responseEntityMono =
                matPostService.getOne(matPostId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new PostNotFoundException(matPostId)));

        return responseEntityMono;
    }

    @GetMapping("/search")
    public Flux<MatPostResponse> getSearchMatPost(@RequestParam("keyword") String keyword) {

        Flux<MatPostResponse> responseEntityFlux = matPostService.findPostByKeyword(keyword);

        return responseEntityFlux;
    }

    @PostMapping
    public Mono<ResponseEntity<MatPostResponse>> saveMatPost( @RequestBody @Validated Mono<SaveMatPostRequest> request) {

        Mono<ResponseEntity<MatPostResponse>> responseEntityMono = request
                .flatMap(matPostService::save)
                .map(mp -> new ResponseEntity<>(mp, HttpStatus.CREATED));

        return responseEntityMono;
     }


    @PatchMapping("/{post-id}")
    public Mono<ResponseEntity<MatPostResponse>> updateMatPost(@RequestBody Mono<UpdateMatPostRequest> request, @PathVariable("post-id") Long postId) {

        Mono<ResponseEntity<MatPostResponse>> responseEntityMono = request
                .flatMap((UpdateMatPostRequest updateMatPostRequest) -> matPostService.update(updateMatPostRequest, postId))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());

        return responseEntityMono;
    }


    @DeleteMapping("/{post-id}")
    public Mono<ResponseEntity<Void>> deleteMatPost(@PathVariable("post-id") Long postId) {

        Mono<ResponseEntity<Void>> responseEntityMono = matPostService.delete(postId)
                .map(response -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.noContent().build());


        return responseEntityMono;
    }


    @GetMapping("/specific/{post-id}")
    public Mono<ResponseEntity<PostUserSpecificInfo>> getSpecific(@PathVariable("post-id") Long postId) {

        Mono<ResponseEntity<PostUserSpecificInfo>> responseEntityMono = matPostService.getPost( postId)
                .map(ResponseEntity::ok);

        return responseEntityMono;
    }

}
