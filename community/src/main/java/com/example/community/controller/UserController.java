package com.example.community.controller;

import com.example.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
