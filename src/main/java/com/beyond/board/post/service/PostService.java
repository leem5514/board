package com.beyond.board.post.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetailResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.author.service.AuthorService;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.PostDetailResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.dto.PostUpdateDto;
import com.beyond.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;

    // Service 또는 Repository 을 autowired 할지는 상황에 따라서 상이하나
    // service 레벨에서 코드가 고도화돼있고 코드의 중복이 심할경우, service 레이어를 autowired
    // But, 순환참조 가 발생하는 경우 repository을 autowired
    @Autowired
    public PostService(PostRepository postRepository, AuthorService authorService, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorService = authorService;
        this.authorRepository = authorRepository;
    }

    public Page<PostListResDto> postList(Pageable pageable) {
//        List<Post> postList = postRepository.findAll();
//        List<Post> postList = postRepository.findAllLeftJoin(); -> N+1 발생
//        List<Post> postList = postRepository.findAllFetch();
//        List<PostListResDto> PostListResDtos = new ArrayList<>();
//        for(Post p : postList) {
//            PostListResDtos.add(p.listFromEntity());
//        }
//        return PostListResDtos;
        //Page<Post> posts = postRepository.findAll(pageable);
        Page<Post> posts = postRepository.findByAppointment(pageable, "N");
        Page<PostListResDto> postListResDtos = posts.map(a->a.listFromEntity());

        return postListResDtos;

    }
    public Page<PostListResDto> postListPage(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostListResDto> postListResDtos = posts.map(a->a.listFromEntity());

        return postListResDtos;
    }

    // authorService에서 author객체를 찾아 post의 toEntity에서 넘기고
    // jpa가 author 객체에서 author_id을 찾아서 db 에는 author_id 가 들어감
    public Post postCreate(PostSaveReqDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = authorService.authorFindByEmail(email);
        List<GrantedAuthority> authorities = new ArrayList<>();

        String appointment = null;
        LocalDateTime appointmentTime = null;
        if (dto.getAppointment().equals("Y") && !dto.getAppointmentTime().isEmpty()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            appointmentTime = LocalDateTime.parse(dto.getAppointmentTime(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if(appointmentTime.isBefore(now)){
                throw new IllegalArgumentException("사건압력이 잘못되었습니다.");
            }
        }
        System.out.println(dto);
        Post post = postRepository.save(dto.toEntity(author, appointmentTime));

        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    public PostDetailResDto postDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Post not found"));
        return post.detailFromEntity();
    }

    @Transactional
    public void deletePost(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Post not found"));
        if(!post.getAuthor().getEmail().equals(email)) {
            throw new IllegalArgumentException("본인의 게시글이 아닙니다.");
        }
        postRepository.delete(post);
    }
    @Transactional
    public Post updatePost(Long id, PostUpdateDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Post not found"));

        if (!post.getAuthor().getEmail().equals(email)) {
            throw new IllegalArgumentException("본인의 게시글이 아닙니다.");
        }
        post.updatePost(dto);
        return postRepository.save(post);
    }

}