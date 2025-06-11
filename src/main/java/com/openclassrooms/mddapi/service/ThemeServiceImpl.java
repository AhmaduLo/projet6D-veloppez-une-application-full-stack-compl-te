package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeServiceImpl implements ThemeService{


    @Autowired
    private ThemeRepository themeRepository;

    @Override
    public ThemeDto createTheme(ThemeDto themeDto) {
        if (themeRepository.existsByName(themeDto.getName())) {
            throw new RuntimeException("Ce thème existe déjà");
        }

        // Construction d'un nouvel objet Theme à partir du DTO
        Theme theme = Theme.builder()
                .name(themeDto.getName())
                .build();

        Theme saved = themeRepository.save(theme);

        // Retourne un DTO avec les données du thème enregistré
        return new ThemeDto(saved.getId(), saved.getName(), null, null);
    }

    @Override
    public List<ThemeDto> getAllThemes() {
        // Conversion des entités Theme en DTO
        return themeRepository.findAll().stream()
                .map(t -> new ThemeDto(t.getId(), t.getName(), null, null))
                .collect(Collectors.toList());
    }

    @Override
    public ThemeDto getThemeById(Long id) {
        // Recherche du thème, ou exception si non trouvé
        Theme t = themeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thème introuvable"));
        return new ThemeDto(t.getId(), t.getName(), null, null);
    }
}
