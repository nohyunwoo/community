package com.example.community.controller;

import com.example.community.dto.PostRequestDTO;
import com.example.community.entity.Comment;
import com.example.community.entity.Post;
import com.example.community.entity.Post_like;
import com.example.community.entity.User;
import com.example.community.repository.CommentRepository;
import com.example.community.repository.PostLikeRepository;
import com.example.community.repository.PostRepository;
import com.example.community.repository.UserRepository;
import com.example.community.security.CustomUserDetails;
import com.example.community.service.CommentService;
import com.example.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final PostLikeRepository postLikeRepository;

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

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postService.findByIdAndIncreaseCount(id); // 조회 + 증가
        List<Comment> commentByPostId = commentService.getCommentByPostId(id);

        model.addAttribute("post", post);
        model.addAttribute("comments", commentByPostId);
        return "postView";
    }

    @PostMapping("/post/like")
    @ResponseBody
    public void increaseLike(@RequestParam Long postId,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails){

        Long userId = customUserDetails.getId();
        Post post = postService.findById(postId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        Post_like postLike = new Post_like(post, user);
        postLike.increaseLike();
        postLikeRepository.save(postLike);
    }
}
