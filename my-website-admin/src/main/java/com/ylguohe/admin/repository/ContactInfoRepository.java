package com.ylguohe.admin.repository;

import com.ylguohe.admin.entity.ContactInfo;
import com.ylguohe.admin.entity.enums.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {
    List<ContactInfo> findByTypeAndActiveTrueOrderBySortOrder(ContactType type);
    List<ContactInfo> findByActiveTrueOrderBySortOrder();
    List<ContactInfo> findAllByOrderBySortOrder();
}
