package com.beyond.board.post.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetailResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.service.AuthorService;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.PostDetailResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.dto.PostUpdateDto;
import com.beyond.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final AuthorService authorService;

    // Service 또는 Repository 을 autowired 할지는 상황에 따라서 상이하나
    // service 레벨에서 코드가 고도화돼있고 코드의 중복이 심할경우, service 레이어를 autowired
    // But, 순환참조 가 발생하는 경우 repository을 autowired
    @Autowired
    public PostService(PostRepository postRepository, AuthorService authorService) {
        this.postRepository = postRepository;
        this.authorService = authorService;
    }

    public List<PostListResDto> postList() {
//        List<Post> postList = postRepository.findAll();
//        List<Post> postList = postRepository.findAllLeftJoin(); -> N+1 발생
        List<Post> postList = postRepository.findAllFetch();
        List<PostListResDto> PostListResDtos = new ArrayList<>();
        for(Post p : postList) {
            PostListResDtos.add(p.listFromEntity());
        }
        return PostListResDtos;
    }

    // authorService에서 author객체를 찾아 post의 toEntity에서 넘기고 jpa가 author 객체에서 author_id을 찾아서 db 에는 author_id 가 들어감
    @Transactional
    public Post postCreate(PostSaveReqDto dto) {
        Author author = authorService.authorFindByEmail(dto.getEmail());
        Post post = postRepository.save(dto.toEntity(author));
        return post;
    }
    public PostDetailResDto postDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Post not found"));
        return post.detailFromEntity();
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
    @Transactional
    public Post updatePost(Long id, PostUpdateDto dto) {
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Post not found"));
        post.updatePost(dto);
        return postRepository.save(post);
    }

}