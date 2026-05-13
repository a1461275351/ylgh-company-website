package com.ylguohe.admin.service;

import com.ylguohe.admin.entity.HeroBanner;
import com.ylguohe.admin.exception.ResourceNotFoundException;
import com.ylguohe.admin.repository.HeroBannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeroBannerService {

    private final HeroBannerRepository repository;

    public List<HeroBanner> findAll() {
        return repository.findAllByOrderBySortOrder();
    }

    public List<HeroBanner> findAllActive() {
        return repository.findByActiveTrueOrderBySortOrder();
    }

    public HeroBanner findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HeroBanner", id));
    }

    public HeroBanner save(HeroBanner banner) {
        return repository.save(banner);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
