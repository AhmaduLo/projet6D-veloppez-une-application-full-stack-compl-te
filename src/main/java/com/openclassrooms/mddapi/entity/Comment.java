package com.openclassrooms.mddapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    private User author;

    @ManyToOne
    private Article article;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}

