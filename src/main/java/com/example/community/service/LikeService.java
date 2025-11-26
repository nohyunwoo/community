package com.example.community.service;

import com.example.community.entity.Post;
import com.example.community.entity.Post_like;
import com.example.community.entity.User;
import com.example.community.repository.PostLikeRepository;
import com.example.community.repository.PostRepository;
import com.example.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final RedisTemplate<String, String> redisTemplate;
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void likePost(Long postId, Long userId){
        String redisCountKey = "like_count:post:" + postId;
        String userLikeKey = "post_like:" + postId + ":" + userId;

        if(Boolean.TRUE.equals(redisTemplate.hasKey(userLikeKey))){
            throw new RuntimeException("이미 누른 좋아요를 누른 사용자입니다.");
        }

        redisTemplate.opsForValue().increment(redisCountKey);

        redisTemplate.opsForValue().set(userLikeKey, "true");

        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("게시글 없음"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자 없음"));

        Post_like postLike = new Post_like(post, user);

        postLikeRepository.save(postLike);
    }

    public Long getLikeCount(Long postId) {
        String redisKey = "like_count:post:" + postId;
        String countStr = redisTemplate.opsForValue().get(redisKey);

        if (countStr != null) {
            return Long.parseLong(countStr);
        }

        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("게시글 없음"));

        Long count = postLikeRepository.countByPost(post);
        redisTemplate.opsForValue().set(redisKey, String.valueOf(count));
        return count;
    }
}
