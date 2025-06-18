package com.openclassrooms.mddapi.mapper;


import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.entity.Comment;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleMapper {

//    Convertit une entité Article en ArticleDto.
//    Transforme aussi la liste des commentaires associés en liste de CommentDto.

    public ArticleDto toDto(Article article) {
        List<CommentDto> commentDtos = null;
        // Vérifie si l'article a des commentaires associés
        if (article.getComments() != null) {
            // Transforme chaque Comment en CommentDto
            commentDtos = article.getComments().stream()
                    .map(comment -> new CommentDto(
                            comment.getId(),
                            comment.getContent(),
                            comment.getCreatedAt(),
                            comment.getAuthor().getId(),
                            article.getId(),
                            comment.getAuthor().getUsername()
                    ))
                    .collect(Collectors.toList());
        }

        // Crée et retourne un ArticleDto avec toutes les données nécessaires
        return new ArticleDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt(),
                article.getAuthor().getId(),
                article.getAuthor().getUsername(),
                article.getTheme().getId(),
                article.getTheme().getName(),
                commentDtos
        );
    }

    //Convertit un ArticleDto en entité Article.
    public Article toEntity(ArticleDto dto, User author, Theme theme) {
        return Article.builder()
                .id(dto.getId()) // peut être null pour la création
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(dto.getCreatedAt()) // ou LocalDateTime.now() pour la création
                .author(author)
                .theme(theme)
                .build();
    }
}
