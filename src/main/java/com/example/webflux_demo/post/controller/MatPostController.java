package com.example.webflux_demo.post.controller;


import com.example.webflux_demo.post.entity.dto.MatPostResponse;
import com.example.webflux_demo.post.entity.dto.SaveMatPostRequest;
import com.example.webflux_demo.post.service.MatPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/places/mat-post")
@RequiredArgsConstructor
public class MatPostController {

    private final MatPostService matPostService;

    @GetMapping
    public Mono<ResponseEntity<List<MatPostResponse>>> getAllMatPosts() {
        return matPostService.getAll().collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{mat-post-id}")
    public Mono<ResponseEntity<MatPostResponse>> getOneMatPost(@PathVariable("mat-post-id") Long matPostId) {
        return matPostService.getOne(matPostId)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/search")
    public Flux<MatPostResponse> getSearchMatPost(@RequestParam("keyword") String keyword) {
        return matPostService.findPostByKeyword(keyword);

    }

    @PostMapping
    public Mono<ResponseEntity<Void>> saveMatPost(@RequestBody Mono<SaveMatPostRequest> request) {
        return request.flatMap(matPostService::save)
                .thenReturn(ResponseEntity.created(URI.create("/")).build());
    }
}
