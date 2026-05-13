package com.ylguohe.admin.repository;

import com.ylguohe.admin.entity.ContactInquiry;
import com.ylguohe.admin.entity.enums.InquiryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInquiryRepository extends JpaRepository<ContactInquiry, Long> {
    List<ContactInquiry> findByStatusOrderByCreatedAtDesc(InquiryStatus status);
    List<ContactInquiry> findAllByOrderByCreatedAtDesc();
    long countByStatus(InquiryStatus status);
}
