package com.ylguohe.admin.service;

import com.ylguohe.admin.entity.Statistic;
import com.ylguohe.admin.exception.ResourceNotFoundException;
import com.ylguohe.admin.repository.StatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final StatisticRepository repository;

    public List<Statistic> findAll() {
        return repository.findAllByOrderBySortOrder();
    }

    public List<Statistic> findAllActive() {
        return repository.findByActiveTrueOrderBySortOrder();
    }

    public List<Statistic> findBySection(String section) {
        return repository.findBySectionAndActiveTrueOrderBySortOrder(section);
    }

    public Statistic findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Statistic", id));
    }

    public Statistic save(Statistic stat) {
        return repository.save(stat);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
