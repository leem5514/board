package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetailResDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
//        Optional<Author> optAuthor = authorRepository.findById(id);
        Author author = authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("없는 회원입니다."));
        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto();


        return authorDetailResDto.fromEntity(author);
    }

    public Author authorFindByEmail(String email) {
        Author author = authorRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("해당 이메일 사용자는 없습니다."));
        return author;
    }
}
