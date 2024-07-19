package com.beyond.board.post.domain;

import com.beyond.board.author.domain.Author;
import com.beyond.board.common.BaseTimeEntity;
import com.beyond.board.post.dto.PostDetailResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostUpdateDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 3000)
    private String content;

    // 연관관계에 주인은 FK가 있는
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;

    public PostListResDto listFromEntity() {
//        return PostListResDto.builder().id(this.id).title(this.title).author(this.author).build();
        return PostListResDto.builder().id(this.id).title(this.title).author_email(this.author.getEmail()).build();
    }

    public PostDetailResDto detailFromEntity() {
        return PostDetailResDto.builder().id(this.id).title(this.title).content(this.content).author_email(this.author.getEmail()).createdTime(this.getCreatedTime()).updatedTime(this.getUpdateTime()).build();
    }

    public void updatePost(PostUpdateDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }
}