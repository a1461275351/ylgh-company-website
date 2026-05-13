package com.ylguohe.admin.service;

import com.ylguohe.admin.entity.Qualification;
import com.ylguohe.admin.exception.ResourceNotFoundException;
import com.ylguohe.admin.repository.QualificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QualificationService {

    private final QualificationRepository repository;

    public List<Qualification> findAll() {
        return repository.findAllByOrderBySortOrder();
    }

    public List<Qualification> findAllActive() {
        return repository.findByActiveTrueOrderBySortOrder();
    }

    public Qualification findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Qualification", id));
    }

    public Qualification save(Qualification q) {
        return repository.save(q);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
