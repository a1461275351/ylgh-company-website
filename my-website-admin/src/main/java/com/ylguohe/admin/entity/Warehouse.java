package com.ylguohe.admin.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "warehouses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    private String flag;

    @Column(nullable = false)
    private String size;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String features;

    private String capabilities;

    @Column(columnDefinition = "TEXT")
    private String contactInfo;

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
