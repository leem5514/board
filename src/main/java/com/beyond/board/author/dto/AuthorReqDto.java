package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorReqDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Enum role;

    public Author toEntity() {
        Author author = new Author(this.id, this.name, this.email, this.password, (Role) this.role);
        return author;
    }
}
