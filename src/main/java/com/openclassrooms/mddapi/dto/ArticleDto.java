package com.openclassrooms.mddapi.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long authorId;
    private Long themeId;
    private List<CommentDto> comments;
}

