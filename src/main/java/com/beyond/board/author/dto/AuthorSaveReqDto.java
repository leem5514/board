package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

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


    public Author toEntity(String encodedPassword) {
        Author author = Author.builder()
                .password(encodedPassword)
                .name(this.name)
                .email(this.email)
                .posts(new ArrayList<>())
                .role(this.role)
                .build();
        return author;
    }
}
