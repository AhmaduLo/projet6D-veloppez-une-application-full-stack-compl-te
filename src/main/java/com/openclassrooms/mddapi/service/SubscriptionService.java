package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubscriptionDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SubscriptionService {
    SubscriptionDto subscribeToTheme(HttpServletRequest request, Long themeId);
    void unsubscribeFromTheme(HttpServletRequest request, Long themeId);
    List<SubscriptionDto> getUserSubscriptions(HttpServletRequest request);
}
