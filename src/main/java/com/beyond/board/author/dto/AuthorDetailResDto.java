package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.post.domain.Post;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDetailResDto {
    private long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private int postCounts;
    private LocalDateTime createdTime;




    // Author에서 구현하지 않고 AuthorDetail에서도 구현 가능(this.xx -> get.xx)
    public AuthorDetailResDto fromEntity(Author author) {
        return AuthorDetailResDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .password(author.getPassword())
                .role(author.getRole())
                .postCounts(author.getPosts().size())
                .createdTime(author.getCreatedTime())
                .build();
    }
}