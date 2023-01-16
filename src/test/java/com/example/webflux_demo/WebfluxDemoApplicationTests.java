package com.example.webflux_demo;

import com.example.webflux_demo.comment.entity.Comment;
import com.example.webflux_demo.like.dto.LikeRequest;
import com.example.webflux_demo.like.repository.LikesRepository;
import com.example.webflux_demo.like.service.LikeService;
import com.example.webflux_demo.member.dto.PostUserSpecificInfo;
import com.example.webflux_demo.post.entity.MatPost;
import com.example.webflux_demo.post.repository.MatPostRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class WebfluxDemoApplicationTests {

    @Autowired
    private MatPostRepository matPostRepository;
    @Autowired
    private LikesRepository likesRepository;


    @Test
    void test() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);

        for (int i = 0; i<30; i++) {
            executorService.execute(() -> {
                // System.nanoTime()은 JVM의 high-resolution time resource를
                // (자바의 Real-time Specification for Java - RTSJ 라고하는 스케줄링, 메모리 관리, 쓰레딩, 동기화, 시간, 클럭, 비동기식 이벤트 핸들링의 영역들이 필요로 하는 시간 )
                // 값을 nano초 단위로 반환하며, 시스템이나 실제 시각(시/분/초)와는 아무런 관련없고
                // 경과된 시간을 측정하는데 사용해야 한다고 하여 사용함. (※ nanoTime 은 jvm 별로 상이한 결과가 나올 수 있다고함 )

                System.out.println("첫 번째 스레드 시작시간 : " + System.nanoTime());
                likesRepository.increasePostLikesCount(27L).subscribe();
                latch.countDown();
            });

            executorService.execute(() -> {

                System.out.println("두 번째 스레드 시작시간 : " + System.nanoTime());
                likesRepository.increasePostLikesCount(27L).subscribe();
                latch.countDown();
            });
        }
        latch.await();
        MatPost block = matPostRepository.findById(27L).block();
        System.out.println( "27번째 게시물의 likes 숫자는 " + block.getLikes());
    }

}
