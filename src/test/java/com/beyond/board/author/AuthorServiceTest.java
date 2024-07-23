package com.beyond.board.author;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
@Transactional
public class AuthorServiceTest {

    @Autowired
    public AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;

    // 저장 및 detail 조회
    @Test
    void saveAndFind() {
        AuthorSaveReqDto authorDto = new AuthorSaveReqDto("hongildong", "hongildong@gmail.com", "123456789", Role.ADMIN );
        Author author = authorService.authorCreate(authorDto);
        Author authorDetail = authorRepository.findById(author.getId()).orElseThrow(() -> new EntityNotFoundException("not Found"));

        Assertions.assertEquals(authorDetail.getEmail(), authorDto.getEmail());
    }

    // update
    // 객체 CREATE
    @Test
    void UpdatePost() {
        AuthorSaveReqDto authorSaveReqDto = new AuthorSaveReqDto("hongUpdate", "hongUpdate@gmail.com", "123456789", Role.ADMIN );
        Author author = authorService.authorCreate(authorSaveReqDto);
        // 수정작업(name, password)

        authorService.update(author.getId(), new AuthorUpdateDto("hongUpdateFinish", "987654321") );
        // 수정후 재조회 : name, password 가 맞는지 각각 검증
        Author findAuthor = authorRepository.findById(author.getId()).orElseThrow(() -> new EntityNotFoundException("not Found"));

        Assertions.assertEquals("hongUpdateFinish", findAuthor.getName());
        Assertions.assertEquals("987654321", findAuthor.getPassword());
    }

    // findAll

    @Test
    void FindAllTest(){

        int size = authorService.authorList().size();
        AuthorSaveReqDto dto1 = new AuthorSaveReqDto("hongEx1", "hongEx1@gmail.com", "12345678", Role.ADMIN );
        authorService.authorCreate(dto1);
        AuthorSaveReqDto dto2 = new AuthorSaveReqDto("hongEx2", "hongEx2@gmail.com", "12345678", Role.ADMIN );
        authorService.authorCreate(dto2);
        AuthorSaveReqDto dto3 = new AuthorSaveReqDto("hongEx3", "hongEx3@gmail.com", "12345678", Role.ADMIN );
        authorService.authorCreate(dto3);


        Assertions.assertEquals(size + 3, authorService.authorList().size());
    }
}
