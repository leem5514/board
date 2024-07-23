package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorSaveReqDto {
    private String name;
    private String email;
    private String password;
    //private List<Post> posts;
    private Role role; // 사용자가 String 요청해도 Role 클래스 자동 형변환

    public Author toEntity() {
        Author author = Author.builder()
                .password(this.password)
                .name(this.name)
                .email(this.email)
                .posts(new ArrayList<>())
                .role(this.role)
                .build();
        return author;
    }
}
