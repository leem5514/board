package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostListResDto {
    private Long id;
    private String title;
    // author 객체 그 자체를 리턴하게 되면 author안에 post가 재 참조 되어, 순환 참조 이슈 발생
//    private Author author;
    private String author_email;


}
