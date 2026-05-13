package com.ylguohe.admin.entity;

import com.ylguohe.admin.entity.enums.NewsCategory;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news_articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NewsCategory category;

    private String emoji;

    private String image;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String excerpt;

    @Column(nullable = false)
    @Builder.Default
    private Boolean featured = false;

    @Builder.Default
    private Boolean active = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
        if (featured == null) featured = false;
        if (active == null) active = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
