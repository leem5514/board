package com.beyond.board.author.domain;

import com.beyond.board.author.dto.AuthorDetailResDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.common.BaseTimeEntity;
import com.beyond.board.post.domain.Post;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
// 빌더 어노테이션을 통해서 매개변수의 순서, 매개변수 개수 등을 유연하게 세팅하여 생성자로서 활용
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;


    @Builder
    public Author(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public AuthorListResDto listFromEntity() {
        AuthorListResDto authorListResDto = AuthorListResDto.builder().id(this.id).name(this.name).email(this.email).build();
        return authorListResDto;
    }

    public AuthorDetailResDto detFromEntity() {
        //LocalDateTime createdTime = this.getCreatedTime();
        //String value = createdTime.getYear()+"년" + createdTime.getMonthValue()+"월"+createdTime.getDayOfMonth()+"일";
        AuthorDetailResDto authorDetailResDto = AuthorDetailResDto.builder().id(this.id).name(this.name).email(this.email).createdTime(this.getCreatedTime()).build();
        return authorDetailResDto;
    }


}
