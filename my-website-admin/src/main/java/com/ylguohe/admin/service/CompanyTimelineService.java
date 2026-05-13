package com.ylguohe.admin.service;

import com.ylguohe.admin.entity.CompanyTimeline;
import com.ylguohe.admin.exception.ResourceNotFoundException;
import com.ylguohe.admin.repository.CompanyTimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyTimelineService {

    private final CompanyTimelineRepository repository;

    public List<CompanyTimeline> findAll() {
        return repository.findAllByOrderBySortOrder();
    }

    public CompanyTimeline findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CompanyTimeline", id));
    }

    public CompanyTimeline save(CompanyTimeline timeline) {
        return repository.save(timeline);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
