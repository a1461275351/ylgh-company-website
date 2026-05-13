package com.ylguohe.admin.service;

import com.ylguohe.admin.entity.ContactInquiry;
import com.ylguohe.admin.entity.enums.InquiryStatus;
import com.ylguohe.admin.exception.ResourceNotFoundException;
import com.ylguohe.admin.repository.ContactInquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactInquiryService {

    private final ContactInquiryRepository repository;

    public List<ContactInquiry> findAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    public List<ContactInquiry> findByStatus(InquiryStatus status) {
        return repository.findByStatusOrderByCreatedAtDesc(status);
    }

    public ContactInquiry findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactInquiry", id));
    }

    public ContactInquiry save(ContactInquiry inquiry) {
        return repository.save(inquiry);
    }

    public void updateStatus(Long id, InquiryStatus status, String adminNote) {
        ContactInquiry inquiry = findById(id);
        inquiry.setStatus(status);
        if (adminNote != null) {
            inquiry.setAdminNote(adminNote);
        }
        repository.save(inquiry);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }

    public long countByStatus(InquiryStatus status) {
        return repository.countByStatus(status);
    }
}
