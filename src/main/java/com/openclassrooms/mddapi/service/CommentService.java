package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentService {
    CommentDto addComment(HttpServletRequest request, CommentDto commentDto);
    List<CommentDto> getCommentsByArticleId(Long articleId);
    void deleteComment(Long commentId, HttpServletRequest request);

}
