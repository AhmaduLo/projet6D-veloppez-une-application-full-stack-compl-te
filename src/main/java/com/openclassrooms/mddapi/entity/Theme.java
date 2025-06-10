package com.openclassrooms.mddapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.Flow;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    private List<Article> articles;

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;
}
