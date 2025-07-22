package com.example.community.service;

import com.example.community.dto.PostRequestDTO;
import com.example.community.entity.Post;
import com.example.community.entity.User;
import com.example.community.exception.PostNotFoundException;
import com.example.community.repository.PostRepository;
import com.example.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void savePost(PostRequestDTO postRequestDTO, String username){

        User user = userRepository.findByUsername(username).orElseThrow(()
                -> new RuntimeException("사용자 없음"));

        Post post = new Post();
        post.setTitle(postRequestDTO.getTitle());
        post.setContent(postRequestDTO.getContent());
        post.setUser(user);

        postRepository.save(post);
    }

    @Transactional
    public Post findById(Long id){

        Optional<Post> optionPost = postRepository.findById(id);

        if(optionPost.isEmpty()){
            throw new PostNotFoundException("해당 게시글이 없습니다. id=" + id);
        }

        Post post = optionPost.get();
        return post;
    }

    @Transactional
    public Post findByIdAndIncreaseCount(Long id){
        Optional<Post> optionPost = postRepository.findById(id);

        if(optionPost.isEmpty()){
            throw new PostNotFoundException("해당 게시글이 없습니다. id=" + id);
        }

        Post post = optionPost.get();
        post.increaseCount();
        return post;
    }
}
