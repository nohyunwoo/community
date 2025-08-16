package com.example.community.repository;

import com.example.community.entity.Post;
import com.example.community.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p From Post p ORDER BY p.createdAt DESC")
    List<Post> findTop5Posts(Pageable pageable);

    @Query(value = "SELECT p FROM Post p JOIN FETCH p.user",
            countQuery ="SELECT COUNT(p) FROM Post p")
    Page<Post> findAllWithUser(Pageable pageable);
}
