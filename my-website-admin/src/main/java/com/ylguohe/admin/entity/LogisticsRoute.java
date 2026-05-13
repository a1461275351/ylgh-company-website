package com.ylguohe.admin.entity;

import com.ylguohe.admin.entity.enums.LogisticsType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "logistics_routes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogisticsRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogisticsType type;

    @Column(nullable = false)
    private String name;

    private String cities;

    private String frequency;

    private String transitTime;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String icon;

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
