package com.example.community.controller;

import com.example.community.entity.Post;
import com.example.community.repository.PostRepository;
import com.example.community.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostRepository postRepository;

    @GetMapping("/home")
    public String home(Model model, Authentication authentication){

        List<Post> postList = postRepository.findTop5Posts(PageRequest.of(0, 5));
        model.addAttribute("postList", postList);

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("displayName", user.getDisplayName());

        }
        return "home";
    }
}
