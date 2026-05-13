package com.ylguohe.admin.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "company_timeline")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyTimeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "milestone_year", nullable = false)
    private String year;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String icon;

    @Builder.Default
    private Boolean highlight = false;

    @Column(nullable = false)
    private Integer sortOrder;

    @PrePersist
    protected void onCreate() {
        if (highlight == null) highlight = false;
        if (sortOrder == null) sortOrder = 0;
    }
}
