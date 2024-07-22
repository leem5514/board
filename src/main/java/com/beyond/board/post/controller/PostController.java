package com.beyond.board.post.controller;

import com.beyond.board.post.dto.PostDetailResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.dto.PostUpdateDto;
import com.beyond.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
//@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/list")
    public String postList(Model model, @PageableDefault(size = 5, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("postList", postService.postList(pageable));
        return "post/post_list";
    }
    @GetMapping("post/list/page")
    @ResponseBody
    //pageAble 요청 방법 : localhost:8080/post/list?size=10&page
    public Page<PostListResDto> postListPage(@PageableDefault(size = 5, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable ) {
        return postService.postListPage(pageable);
    }


    @GetMapping("post/create")
    public String postRegister() {
        return "/post/post_register";
    }

    @PostMapping("/post/create")
    public String postCreate(@ModelAttribute PostSaveReqDto dto){
        postService.postCreate(dto);
        return "redirect:/post/list";
    }
    @GetMapping("/post/detail/{id}")
    public String postDetailResDto(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.postDetail(id));
        return "post/post_detail";
    }

    @PostMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id, @ModelAttribute PostUpdateDto dto, Model model) {
        model.addAttribute("post", postService.updatePost(id, dto));
        return "redirect:/post/detail/" + id;
    }
//    @GetMapping("post/list/page")
//    @ResponseBody
//    //pageAble 요청 방법 : localhost:8080/post/list?size=10&page
//    public Page<PostListResDto> postListPage(@PageableDefault(size = 5, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable ) {
//        return postService.postListPage(pageable);
//    }


    @GetMapping("post/delete/{id}")
    public String postDelete(@PathVariable Long id,Model model) {
        postService.deletePost(id);
        return "redirect:/post/list";
    }

}