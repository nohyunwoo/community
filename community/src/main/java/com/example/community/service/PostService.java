package com.example.community.service;

import com.example.community.dto.PostRequestDTO;
import com.example.community.entity.Post;
import com.example.community.entity.User;
import com.example.community.repository.PostRepository;
import com.example.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
