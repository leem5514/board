package com.beyond.board.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorResDto {
    private Long id;
    private String name;
    private String email;

}
