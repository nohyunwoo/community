package com.example.community.controller;

import com.example.community.entity.User;
import com.example.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String Login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@RequestParam String displayName,
                               @RequestParam String username,
                               @RequestParam String password){
        User user = new User();
        user.setDisplayName(displayName);
        user.setUsername(username);
        String encode = passwordEncoder.encode(password);
        user.setPassword(encode);
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }
}
