package com.ylguohe.admin.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "vehicle_destinations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDestination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String country;

    private String flag;

    private String description;

    private String exportCount;

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
