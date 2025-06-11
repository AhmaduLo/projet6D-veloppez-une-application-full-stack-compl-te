package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.entity.Subscription;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.mapper.SubscriptionMapper;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.token.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    private User getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @Override
    public SubscriptionDto subscribeToTheme(HttpServletRequest request, Long themeId) {
        User user = getAuthenticatedUser(request);
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Thème introuvable"));

        if (subscriptionRepository.existsByUserIdAndThemeId(user.getId(), themeId)) {
            throw new RuntimeException("Déjà abonné à ce thème");
        }

        // Création et sauvegarde de l'abonnement
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setTheme(theme);
        Subscription saved = subscriptionRepository.save(subscription);

        return subscriptionMapper.toDto(saved);
    }

    @Override
    public void unsubscribeFromTheme(HttpServletRequest request, Long themeId) {
        User user = getAuthenticatedUser(request);
        Subscription sub = subscriptionRepository.findByUserIdAndThemeId(user.getId(), themeId)
                .orElseThrow(() -> new RuntimeException("Abonnement non trouvé"));
        // Suppression de l'abonnement
        subscriptionRepository.delete(sub);
    }

    @Override
    public List<SubscriptionDto> getUserSubscriptions(HttpServletRequest request) {
        User user = getAuthenticatedUser(request);
        // Filtrer les abonnements appartenant à l'utilisateur, les mapper en DTO et retourner la liste
        return subscriptionRepository.findAll().stream()
                .filter(s -> s.getUser().getId().equals(user.getId()))
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());
    }
}
