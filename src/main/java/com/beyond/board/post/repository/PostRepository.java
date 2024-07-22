package com.beyond.board.post.repository;

import com.beyond.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Page<Post> : List<Post> + 해당요소의 Page정보
    // Pageable : PageNumber(몇 번 페이지 인지)  + size(한 페이지에 몇개씩 인지) + 정렬조건
    Page<Post> findAll(Pageable pageable);

    // jpql 을 적용하여 네이밍룰을 통한 방식이 아닌 메서드 생성
    // select a.*, p.* from post p  left join author a on p.author_id= a.id;
    @Query("select p from Post p left join fetch p.author")
    List<Post> findAllFetch();

    // select p.* from post p left join author a on p.author_id= a.id;
    // 아래의 left join문은 author 객체를 통한 조건문으로 post filter 할 때 사용 -> N+1 발생
    @Query("select p from Post p left join p.author")
    List<Post> findAllLeftJoin();

    Page<Post> findByAppointment(Pageable pageable, String appointment);
}
