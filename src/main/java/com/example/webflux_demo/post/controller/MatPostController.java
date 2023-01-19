package com.example.webflux_demo.post.controller;


import com.example.webflux_demo.comment.dto.MultiResponseDto;
import com.example.webflux_demo.exception.CustomErrorCode;
import com.example.webflux_demo.exception.PostNotFoundException;
import com.example.webflux_demo.post.entity.dto.MatPostResponse;
import com.example.webflux_demo.post.entity.dto.SaveMatPostRequest;
import com.example.webflux_demo.post.entity.dto.UpdateMatPostRequest;
import com.example.webflux_demo.post.service.MatPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/places/posts")
@RequiredArgsConstructor
public class MatPostController {
    private final MatPostService matPostService;

    @GetMapping
    public Flux<MatPostResponse> getAllMatPosts(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "3") int size) {

        return matPostService.getAll(page, size);
    }

//    @GetMapping("/{post-id}")
//    public Mono<ResponseEntity<MatPostResponse>> getOneMatPost(@PathVariable("post-id") Long matPostId) {
//
//        Mono<ResponseEntity<MatPostResponse>> responseEntityMono =
//                matPostService.getOne(matPostId)
//                        .map(ResponseEntity::ok)
//                        .switchIfEmpty(Mono.defer(() -> Mono.error(new PostNotFoundException(CustomErrorCode.POST_NOT_FOUND))));
//
//        return responseEntityMono;
//    }

    @GetMapping("/search/title")
    public Flux<MatPostResponse> getSearchMatPost(@RequestParam("keyword") String keyword) {

        Flux<MatPostResponse> responseEntityFlux = matPostService.findPostByKeyword(keyword).switchIfEmpty(Mono.error(new PostNotFoundException(CustomErrorCode.POST_NOT_FOUND)));
        ;

        return responseEntityFlux;
    }

    @GetMapping("/search/content")
    public Flux<MatPostResponse> getSearchMatPostContent(@RequestParam("keyword") String keyword) {

        Flux<MatPostResponse> responseEntityFlux = matPostService.findPostByContentKeyword(keyword).switchIfEmpty(Mono.error(new PostNotFoundException(CustomErrorCode.POST_NOT_FOUND)));
        ;

        return responseEntityFlux;
    }

    @PostMapping
    public Mono<ResponseEntity<MatPostResponse>> saveMatPost(@RequestBody @Validated Mono<SaveMatPostRequest> request) {

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
                .switchIfEmpty(Mono.defer(() -> Mono.error(new PostNotFoundException(CustomErrorCode.POST_NOT_FOUND))));

        return responseEntityMono;
    }


    @DeleteMapping("/{post-id}")
    public Mono<ResponseEntity<Void>> deleteMatPost(@PathVariable("post-id") Long postId) {

        Mono<ResponseEntity<Void>> responseEntityMono = matPostService.delete(postId)
                .map(response -> ResponseEntity.noContent().<Void>build())
                .switchIfEmpty(Mono.defer(() -> Mono.error(new PostNotFoundException(CustomErrorCode.POST_NOT_FOUND))));

        return responseEntityMono;
    }


    @GetMapping("/{post-id}")
    public Mono<ResponseEntity<MultiResponseDto>> getSpecific(@PathVariable("post-id") Long postId) {

        Mono<ResponseEntity<MultiResponseDto>> map = matPostService.getPost(postId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new PostNotFoundException(CustomErrorCode.POST_NOT_FOUND))));

        return map;
    }
}
