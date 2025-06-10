package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class SubscriptionDto {
    private Long id;
    private Long userId;
    private Long themeId;
}

