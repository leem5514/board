package com.beyond.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailResDto {
    private Long id;
    private String title;
    private String content;
    private String author_email;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
