package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.entity.Comment;
import com.openclassrooms.mddapi.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getAuthor().getId(),
                comment.getArticle().getId()
        );
    }

    public Comment toEntity(CommentDto dto, User author, Article article) {
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setCreatedAt(dto.getCreatedAt());
        comment.setAuthor(author);
        comment.setArticle(article);
        return comment;
    }
}
