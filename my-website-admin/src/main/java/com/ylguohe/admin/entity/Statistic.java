package com.ylguohe.admin.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column(name = "stat_value", nullable = false)
    private String value;

    private String unit;

    private String icon;

    private String section;

    @Column(nullable = false)
    private Integer sortOrder;

    @Builder.Default
    private Boolean active = true;

    @PrePersist
    protected void onCreate() {
        if (active == null) active = true;
        if (sortOrder == null) sortOrder = 0;
    }
}
