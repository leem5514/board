package com.beyond.board.author;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.dto.AuthorDetailResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.author.service.AuthorService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@SpringBootTest
@Transactional
public class AuthorServiceMockTest {
    @Autowired
    private AuthorService authorService;
//    @Autowired
//    private AuthorRepository authorRepository;
    // 가짜 객체를 만드는 작업 = 목킹
    @MockBean
    private AuthorRepository authorRepository;


    @Test
    public void findAuthorDetailTest() {
        AuthorSaveReqDto author = new AuthorSaveReqDto("testCase", "test@naver.com", "12345678", Role.ADMIN);
        Author author1 = authorService.authorCreate(author);
        //Author myAuthor = Author.builder().id(1L).name("myTest").email("myTest@naver.com").build();
        AuthorDetailResDto dto = authorService.authorDetail(1L);
        //Author savedAuthor = authorRepository.findById(author1.getId()).orElseThrow(() ->new EntityNotFoundException("Not found"));


        Mockito.when(authorRepository.findById(author1.getId())).thenReturn(Optional.of(author1));
        Author savedAuthor = authorRepository.findById(author1.getId()).orElseThrow(() -> new EntityNotFoundException("Author not found"));
        Assertions.assertEquals(dto.getEmail(), author1.getEmail(), savedAuthor.getEmail());

    }
}
