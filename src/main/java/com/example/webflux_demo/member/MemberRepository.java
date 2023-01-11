package com.example.webflux_demo.member;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MemberRepository extends ReactiveCrudRepository<Member,Long> {
}
