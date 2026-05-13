package com.ylguohe.admin.service;

import com.ylguohe.admin.entity.ContactInfo;
import com.ylguohe.admin.entity.enums.ContactType;
import com.ylguohe.admin.exception.ResourceNotFoundException;
import com.ylguohe.admin.repository.ContactInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactInfoService {

    private final ContactInfoRepository repository;

    public List<ContactInfo> findAll() {
        return repository.findAllByOrderBySortOrder();
    }

    public List<ContactInfo> findByType(ContactType type) {
        return repository.findByTypeAndActiveTrueOrderBySortOrder(type);
    }

    public List<ContactInfo> findAllActive() {
        return repository.findByActiveTrueOrderBySortOrder();
    }

    public ContactInfo findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactInfo", id));
    }

    public ContactInfo save(ContactInfo info) {
        return repository.save(info);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
