package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ThemeDto;

import java.util.List;

public interface ThemeService {
    ThemeDto createTheme(ThemeDto themeDto);
    List<ThemeDto> getAllThemes();
    ThemeDto getThemeById(Long id);
}
