package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ArticleService {
    ArticleDto createArticle(HttpServletRequest request, ArticleDto dto);
    List<ArticleDto> getAllArticles();
    ArticleDto getArticleById(Long id);
}