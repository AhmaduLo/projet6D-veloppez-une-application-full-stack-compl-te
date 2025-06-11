package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.token.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public ArticleDto createArticle(HttpServletRequest request, ArticleDto dto) {
        // Récupérer l'utilisateur via le token
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // Récupérer le thème
        Theme theme = themeRepository.findById(dto.getThemeId())
                .orElseThrow(() -> new RuntimeException("Thème introuvable"));

        // Utilise le mapper pour créer un Article à partir du DTO + user + thème
        Article article = articleMapper.toEntity(dto, user, theme);
        article.setCreatedAt(LocalDateTime.now());

        return articleMapper.toDto(articleRepository.save(article));
    }

    @Override
    public List<ArticleDto> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(articleMapper::toDto)// Convertit chaque entité Article en DTO
                .collect(Collectors.toList());
    }

    @Override
    public ArticleDto getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article introuvable"));
        return articleMapper.toDto(article); // Retourne le DTO correspondant
    }
}
