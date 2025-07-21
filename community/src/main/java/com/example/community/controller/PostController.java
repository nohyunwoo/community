package com.example.community.controller;

import com.example.community.dto.PostRequestDTO;
import com.example.community.entity.Post;
import com.example.community.entity.User;
import com.example.community.repository.PostRepository;
import com.example.community.repository.UserRepository;
import com.example.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping("/board")
    public String board(Model model){
        List<Post> postList = postRepository.findAll();
        model.addAttribute("postList", postList);
        return "board";
    }
    @GetMapping("/post")
    public String post(){

        return "post";
    }

    @PostMapping("/post")
    public String savePost(@ModelAttribute PostRequestDTO postRequestDTO,
                           Principal principal){
        postService.savePost(postRequestDTO, principal.getName());

        return "redirect:/board";
    }
}
