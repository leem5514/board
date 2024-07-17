package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorReqDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public void save(AuthorReqDto dto) {
        if(dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        Author author = dto.toEntity();
        if(authorRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다");
        }
        authorRepository.save(author);
    }

    public List<AuthorResDto> authorList() {
        List<AuthorResDto> authorResDtos = new ArrayList<>();
        List<Author> authorList = authorRepository.findAll();
        for(Author author : authorList) {
            authorResDtos.add(author.listFromEntity());

        }
        //return memberResDto;
        return authorResDtos;
    }



}
