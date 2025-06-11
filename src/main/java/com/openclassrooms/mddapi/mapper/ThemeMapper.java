package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.entity.Theme;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ThemeMapper {


    // Convertit Theme → ThemeDto
    public ThemeDto toDto(Theme theme) {
        return new ThemeDto(
                theme.getId(),
                theme.getName(),
                theme.getArticles() != null ?
                        theme.getArticles().stream()
                                .map(article -> article.getId())
                                .collect(Collectors.toList()) :
                        null,
                theme.getSubscriptions() != null ?
                        theme.getSubscriptions().stream()
                                .map(sub -> sub.getId())
                                .collect(Collectors.toList()) :
                        null
        );
    }

    // Convertit ThemeDto → Theme (sans les relations)
    public Theme toEntity(ThemeDto dto) {
        return Theme.builder()
                .id(dto.getId())
                .name(dto.getName())
                // Les articles et subscriptions ne sont pas définis ici
                .build();
    }
}
