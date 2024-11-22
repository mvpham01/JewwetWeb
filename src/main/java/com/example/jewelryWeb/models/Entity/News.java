package com.example.jewelryWeb.models.Entity;

import lombok.*;

import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String image;

    private LocalDateTime publishedAt;

    private Boolean isActive;
}