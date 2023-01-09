package com.example.webflux_demo.post.service;

import com.example.webflux_demo.post.entity.MatPost;
import com.example.webflux_demo.post.entity.dto.MatPostResponse;
import com.example.webflux_demo.post.entity.dto.SaveMatPostRequest;
import com.example.webflux_demo.post.repository.MatPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatPostService {

    private final MatPostRepository matPostRepository;

    public Flux<MatPostResponse> getAll(){

        Flux<MatPostResponse> responseFlux = matPostRepository.findAll()
                .map(MatPostResponse::from);
        return responseFlux;
    }

    public Mono<MatPostResponse> getOne(Long matPostId) {

        Mono<MatPostResponse> responseFlux = matPostRepository.findById(matPostId)
                .map(MatPostResponse::from);
        return responseFlux;
    }

    public Flux<MatPostResponse> findPostByKeyword(String keyword) {

        Flux<MatPostResponse> listMono = matPostRepository.searchMatPostByKeyword(keyword)
                .map(MatPostResponse::from);
        return listMono;
    }

    public Mono<Void> save(SaveMatPostRequest request) {

        Mono<Void> mono = matPostRepository.save(request.toEntity())
                .then();
        return mono;
    }
}
