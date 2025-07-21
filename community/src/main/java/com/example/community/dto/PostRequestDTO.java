package com.example.community.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDTO {
    private String title;
    private String content;

    public PostRequestDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
