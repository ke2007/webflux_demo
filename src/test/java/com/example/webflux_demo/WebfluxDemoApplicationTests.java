package com.example.webflux_demo;

import com.example.webflux_demo.comment.entity.Comment;
import com.example.webflux_demo.like.dto.LikeRequest;
import com.example.webflux_demo.like.repository.LikesRepository;
import com.example.webflux_demo.like.service.LikeService;
import com.example.webflux_demo.member.dto.PostUserSpecificInfo;
import com.example.webflux_demo.post.entity.MatPost;
import com.example.webflux_demo.post.entity.dto.SaveMatPostRequest;
import com.example.webflux_demo.post.entity.dto.UpdateMatPostRequest;
import com.example.webflux_demo.post.repository.MatPostRepository;
import com.example.webflux_demo.post.service.MatPostService;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@ContextConfiguration(classes = WebfluxDemoApplication.class)
class WebfluxDemoApplicationTests {

    @Autowired
    private MatPostRepository matPostRepository;
    @Autowired
    private LikeService likesRepository;
    @Autowired
    private MatPostService matPostService;

    @Test
    void test() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0 ; i < 40; i++ ) {
            executorService.execute(() -> {


                LikeRequest build = LikeRequest.builder().likesCheck(1).build();

                System.out.println("첫 번째 스레드 시작시간 : " + System.currentTimeMillis());
                System.out.println("첫 번째 스레드 시작시간(nanoTime) : " + System.nanoTime());
                likesRepository.increaseLikes(build,9L,2L).subscribe();

                latch.countDown();
            });

            executorService.execute(() -> {
                System.out.println("두 번째 스레드 시작시간 : " + System.currentTimeMillis());
                System.out.println("두 번째 스레드 시작시간(nanoTime) : " + System.nanoTime());
                UpdateMatPostRequest build12 = UpdateMatPostRequest.builder().title("t1st").content("ts1t").star(1).thumbnailUrl("t1e1t").build();
                matPostService.update(build12,9L).subscribe();
                latch.countDown();
            });
            executorService.execute(() -> {


                LikeRequest build1 = LikeRequest.builder().likesCheck(1).build();

                System.out.println("세 번째 스레드 시작시간 : " + System.currentTimeMillis());
                System.out.println("세 번째 스레드 시작시간(nanoTime) : " + System.nanoTime());
                likesRepository.increaseLikes(build1,9L,2L).subscribe();

                latch.countDown();
            });
        }
        latch.await();

        MatPost block = matPostRepository.findById(9L).block();
        System.out.println( "27번째 게시물의 likes 숫자는 " + block.getLikes());
    }

}
