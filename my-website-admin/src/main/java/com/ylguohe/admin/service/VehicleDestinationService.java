package com.ylguohe.admin.service;

import com.ylguohe.admin.entity.VehicleDestination;
import com.ylguohe.admin.exception.ResourceNotFoundException;
import com.ylguohe.admin.repository.VehicleDestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleDestinationService {

    private final VehicleDestinationRepository repository;

    public List<VehicleDestination> findAll() {
        return repository.findAllByOrderBySortOrder();
    }

    public List<VehicleDestination> findAllActive() {
        return repository.findByActiveTrueOrderBySortOrder();
    }

    public VehicleDestination findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VehicleDestination", id));
    }

    public VehicleDestination save(VehicleDestination dest) {
        return repository.save(dest);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }
}
