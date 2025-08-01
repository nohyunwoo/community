package com.example.community.repository;

import com.example.community.entity.Post;
import com.example.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
}
