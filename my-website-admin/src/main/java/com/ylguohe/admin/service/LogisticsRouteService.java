package com.ylguohe.admin.service;

import com.ylguohe.admin.entity.LogisticsRoute;
import com.ylguohe.admin.entity.enums.LogisticsType;
import com.ylguohe.admin.exception.ResourceNotFoundException;
import com.ylguohe.admin.repository.LogisticsRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogisticsRouteService {

    private final LogisticsRouteRepository repository;

    public List<LogisticsRoute> findAll() {
        return repository.findAllByOrderBySortOrder();
    }

    public List<LogisticsRoute> findByType(LogisticsType type) {
        return repository.findByTypeOrderBySortOrder(type);
    }

    public List<LogisticsRoute> findActiveByType(LogisticsType type) {
        return repository.findByTypeAndActiveTrueOrderBySortOrder(type);
    }

    public List<LogisticsRoute> findAllActive() {
        return repository.findByActiveTrueOrderBySortOrder();
    }

    public LogisticsRoute findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LogisticsRoute", id));
    }

    public LogisticsRoute save(LogisticsRoute route) {
        return repository.save(route);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
