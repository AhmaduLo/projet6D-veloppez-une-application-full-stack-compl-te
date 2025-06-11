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
                theme.getDescription(),
                null,
                null
        );
    }

    // Convertit ThemeDto → Theme (sans les relations)
    public Theme toEntity(ThemeDto dto) {
        return Theme.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public ThemeDto toShortDto(Theme theme) {
        return new ThemeDto(
                theme.getId(),
                theme.getName(),
                theme.getDescription(),
                null,
                null
        );
    }
}
