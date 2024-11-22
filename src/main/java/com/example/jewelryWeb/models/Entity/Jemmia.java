package com.example.jewelryWeb.models.Entity;
import lombok.*;

import java.time.LocalDateTime;

import jakarta.persistence.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jemmia")
public class Jemmia {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jemmia_id")
    private Long jemmia_id;
    
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String image;

    private LocalDateTime publishedAt;

    private Boolean isActive;
}