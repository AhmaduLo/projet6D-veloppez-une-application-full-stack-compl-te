package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserIdAndThemeId(Long userId, Long themeId);
    boolean existsByUserIdAndThemeId(Long userId, Long themeId);
}