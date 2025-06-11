package com.openclassrooms.mddapi.mapper;


import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.entity.Subscription;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {

    public SubscriptionDto toDto(Subscription entity) {
        return new SubscriptionDto(
                entity.getId(),
                entity.getUser().getId(),
                entity.getTheme().getId()
        );
    }

    public Subscription toEntity(Long id, Long userId, Long themeId) {
        Subscription subscription = new Subscription();
        subscription.setId(id);

        // User et Theme doivent être récupérés et injectés dans le service
        return subscription;
    }
}
