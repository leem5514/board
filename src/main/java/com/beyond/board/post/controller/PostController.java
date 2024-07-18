package com.beyond.board.post.controller;

import com.beyond.board.post.dto.PostDetailResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/list")
    //@ResponseBody
    public List<PostListResDto> postList() {
        return postService.postList();
    }

    @PostMapping("/post/create")
    public String postController(@RequestBody PostSaveReqDto dto){
        postService.postCreate(dto);
        return "ok";
    }
    @GetMapping("/post/detail/{id}")
    public PostDetailResDto postDetailResDto(@PathVariable Long id) {
        return postService.postDetail(id);
    }

}