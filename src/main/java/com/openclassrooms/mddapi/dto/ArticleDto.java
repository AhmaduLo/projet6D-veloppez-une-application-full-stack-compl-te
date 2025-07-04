package com.openclassrooms.mddapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long authorId;
    private String authorUsername;
    private Long themeId;
    private String themeName;
    private List<CommentDto> comments;


}

