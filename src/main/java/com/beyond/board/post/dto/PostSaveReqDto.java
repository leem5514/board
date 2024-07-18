package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveReqDto {
    private String title;
    private String content;
    // 추후 로그인 기능 이후에는 없어질 dto
    private String email;

    public Post toEntity(Author author) {
        return Post.builder().title(this.title).content(this.content).author(author).build();
    }
}
