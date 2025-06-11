package com.openclassrooms.mddapi.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDto {
    private Long id;
    private String name;
    private String description;
    private List<Long> articleIds;
    private List<Long> subscriptionIds;
}

