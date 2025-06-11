package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.mapper.ThemeMapper;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeServiceImpl implements ThemeService{


    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ThemeMapper themeMapper;

    @Override
    public ThemeDto createTheme(ThemeDto themeDto) {
        if (themeRepository.existsByName(themeDto.getName())) {
            throw new RuntimeException("Ce thème existe déjà");
        }

        Theme theme = themeMapper.toEntity(themeDto);
        Theme saved = themeRepository.save(theme);
        return themeMapper.toDto(saved);
    }

    @Override
    public List<ThemeDto> getAllThemes() {
        // Conversion des entités Theme en DTO
        return themeRepository.findAll().stream()
                .map(themeMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public ThemeDto getThemeById(Long id) {
        // Recherche du thème, ou exception si non trouvé
        Theme t = themeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thème introuvable"));
        return themeMapper.toShortDto(t);
    }
}
