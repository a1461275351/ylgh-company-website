package com.ylguohe.admin.entity;

import com.ylguohe.admin.entity.enums.ContactType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "contact_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType type;

    private String city;
    private String country;
    private String address;
    private String phone;
    private String email;
    private String hours;
    private String warehouseSize;
    private String wechat;
    private String whatsapp;

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
