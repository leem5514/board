package com.beyond.board.post.service;

import com.beyond.board.post.domain.Post;
import com.beyond.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

//@Component
//public class PostScheduler {
//    private final PostRepository postRepository;
//
//    @Autowired
//    public PostScheduler(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }
//
//    // 아래 스케쥴의 cron 부는 각 자리마다 "초 분 시간 일 월 요일" 을 의미
//    // ex ) * * * * * * : 매월 매요일 매일 매분 매초마다 시간
//    // ex ) 0 0 * * * * : 매일 0분 0초에 -> 1시간에 한번
//    // ex ) 0 0 11 * * * : 매일 11시에
//    // ex ) 0 0/1 * * * * : 매일 매시 1분에
//    @Scheduled(cron = "0 0/1 * * * *")
//    @Transactional
//    public void postSchedule() {
//        System.out.println("=== 예약 글쓰기 스케쥴러 시작 === ");
//        Page<Post> posts = postRepository.findByAppointment(Pageable.unpaged(), "Y");
//        for (Post p : posts) {
//            if (p.getAppointmentTime().isBefore(LocalDateTime.now())) {
//                p.updateAppointment("N"); // 더티체킹 구조를 때문에 따로 save 진행하지 않아도 상관 없음
//            }
//        }
//
//    }
//}
