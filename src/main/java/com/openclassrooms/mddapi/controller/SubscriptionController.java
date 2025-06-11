package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/{themeId}")
    public ResponseEntity<SubscriptionDto> subscribe(@PathVariable Long themeId, HttpServletRequest request) {
        return ResponseEntity.ok(subscriptionService.subscribeToTheme(request, themeId));
    }

    @DeleteMapping("/{themeId}")
    public ResponseEntity<?> unsubscribe(@PathVariable Long themeId, HttpServletRequest request) {
        subscriptionService.unsubscribeFromTheme(request, themeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getMySubscriptions(HttpServletRequest request) {
        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(request));
    }
}
