package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetailResDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
// 조회 작업 시 readOnly 설정 시 성능 향상 but , 저장 작업시에는 Transactional 필요
@Transactional(readOnly = true)
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Author authorCreate(AuthorSaveReqDto dto) {
        if (authorRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException(("이미 존재하는 email 입니다."));
        }
        Author author = dto.toEntity();
        // CASCADE persist TEST, REMOVE 테스트는 회원삭제로 대체
        author.getPosts().add(Post.builder().title("가입인사").author(author).content("안녕하세요 "+ dto.getName() +"입니다.").build());
        Author savedAuthor = authorRepository.save(author);
        return savedAuthor;
    }

    public List<AuthorListResDto> authorList() {
        List<AuthorListResDto> authorListResDtos = new ArrayList<>();
        List<Author> authorList = authorRepository.findAll();
        for(Author author : authorList) {
            authorListResDtos.add(author.listFromEntity());
        }
        return authorListResDtos;
    }

    public AuthorDetailResDto authorDetail(Long id) {

        Author author = authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("없는 회원입니다."));
        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto();


        return authorDetailResDto.fromEntity(author);
    }

    public Author authorFindByEmail(String email) {
        Author author = authorRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("해당 이메일 사용자는 없습니다."));
        return author;
    }

    @Transactional
    public void delete(Long id) {
         authorRepository.deleteById(id);
    }
    @Transactional
    public Author update(Long id, AuthorUpdateDto dto) {
        Author author = authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("없는 회원입니다."));
        author.updateAuthor(dto);
        // JPA 가 특정 엔티티의 변경을 자동으로 인지하고 변경사항을 DB에 반영하는 것이 DIRTY CHECKING(변경감지) 이다.
        // 추가와 변경은 차이점 有 -> 추가 상황에 관해서는 DRITY CHECK 불가능 / UPDATE에 관해서만 가능
        // 조건) Transactional 어노테이션 必
        // authorRepository.save(author); // 이것처럼 변경감지로 save 없이도 업데이트 가능
        return author;
    }

}
