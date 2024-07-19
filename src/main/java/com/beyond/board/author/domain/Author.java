package com.beyond.board.author.domain;

import com.beyond.board.author.dto.AuthorDetailResDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.common.BaseTimeEntity;
import com.beyond.board.post.domain.Post;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    // 일반적으로 부모님엔티티(개작 엔티티에) 영향을 끼칠 수 있는 엔티티())
    // CascadeType = ALL 을 걸음으로서 주체(Author) 삭제 시 -> board 도 함께 삭제 / CASCADE.PERSIST
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
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

    public void updateAuthor(AuthorUpdateDto dto) {
        this.name = dto.getName();
        this.password = dto.getPassword();
    }
}
