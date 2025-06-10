package com.openclassrooms.mddapi.dto;


import lombok.Data;

import java.util.List;

@Data
public class ThemeDto {
    private Long id;
    private String name;
    private List<Long> articleIds;
    private List<Long> subscriptionIds;
}

