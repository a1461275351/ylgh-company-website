package com.ylguohe.admin.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "hero_banners")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeroBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tag;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String bgStyle;

    private String image;

    private String ctaText;
    private String ctaLink;
    private String ctaText2;
    private String ctaLink2;

    @Column(nullable = false)
    private Integer sortOrder;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @PrePersist
    protected void onCreate() {
        if (active == null) active = true;
        if (sortOrder == null) sortOrder = 0;
    }
}
