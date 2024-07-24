package com.beyond.board.author.controller;

import com.beyond.board.author.dto.*;
import com.beyond.board.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
//@RestController
public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

//    @GetMapping("/author/list")
//    public List<AuthorListResDto> authorList() {
//        return authorService.authorList();
//    }
    @GetMapping("/author/list")
    public String authorList(Model model) {
        List<AuthorListResDto> authorList = authorService.authorList();
        model.addAttribute("authorList", authorList);
        return "author/author_list";
    }
    @GetMapping("/author/detail/{id}")
    public String AuthorDetailResDto(@PathVariable Long id, Model model) {
//        log.info("get요청, parameter은 " + id);
//        log.info("method명 : authorDetail");
        model.addAttribute("author", authorService.authorDetail(id));
        return "author/author_detail";
    }
    @GetMapping("/author/register")
    public String createAuthor() {
        return "author/author_register";
    }

    @GetMapping("/author/login-screen")
    public String authorLoginScreen() {
        return "author/login-screen";
    }

    @PostMapping("/author/register")
    public String authorCreate(@ModelAttribute AuthorSaveReqDto dto) {
        authorService.authorCreate(dto);
        return "redirect:/";
    }
    @GetMapping("author/delete/{id}")
    public String authorDelete(@PathVariable Long id,Model model) {
        authorService.delete(id);
        return "redirect:/author/list";
    }

    @PostMapping("author/update/{id}")
    public String authorUpdate(@PathVariable Long id, @ModelAttribute AuthorUpdateDto authorUpdateDto, Model model) {
        model.addAttribute("author", authorService.update(id, authorUpdateDto));
        return "redirect:/author/detail/" + id;
    }



}
