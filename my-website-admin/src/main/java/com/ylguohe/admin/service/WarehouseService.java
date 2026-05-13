package com.ylguohe.admin.service;

import com.ylguohe.admin.entity.Warehouse;
import com.ylguohe.admin.exception.ResourceNotFoundException;
import com.ylguohe.admin.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository repository;

    public List<Warehouse> findAll() {
        return repository.findAllByOrderBySortOrder();
    }

    public List<Warehouse> findAllActive() {
        return repository.findByActiveTrueOrderBySortOrder();
    }

    public Warehouse findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", id));
    }

    public Warehouse save(Warehouse warehouse) {
        return repository.save(warehouse);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
