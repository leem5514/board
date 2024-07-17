package com.beyond.board.author.controller;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorReqDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.dto.CommonErrorDto;
import com.beyond.board.author.dto.CommonResDto;
import com.beyond.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthorController {

    @Autowired
    private final AuthorService authorService;
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/author/list")
    public List<AuthorResDto> authorList() {
        List<AuthorResDto> authorList = authorService.authorList();
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "success created", authorList);
        return (List<AuthorResDto>) new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }
    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable int id) {

        return "author/detail";
    }


    @PostMapping("/author/create")
    public String authorCreatePost(@ModelAttribute AuthorReqDto dto, Model model) {
        try {
            authorService.save(dto);
            return "redirect:/author/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());

            return "error";
        }
    }
//    @GetMapping("/author/create")
//    public ResponseEntity<Object> authorCreate(@RequestBody AuthorReqDto dto) {
//        try {
//            authorService.authorCreate(dto);
//            CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "success created", dto);
//            return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            CommonErrorDto commonErrorDto = new ResponseEntity<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
//            return new ResponseEntity<>(commonErrorDto, HttpStatus.BAD_REQUEST);
//        }
//    }
}
