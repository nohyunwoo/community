package com.example.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_displayName", referencedColumnName = "displayName")
    private User user;

    private String bio;

    @OneToMany()
    private List<Post> posts = new ArrayList<>();



}
