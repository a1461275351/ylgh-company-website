package com.ylguohe.admin.repository;

import com.ylguohe.admin.entity.VehicleDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleDestinationRepository extends JpaRepository<VehicleDestination, Long> {
    List<VehicleDestination> findByActiveTrueOrderBySortOrder();
    List<VehicleDestination> findAllByOrderBySortOrder();
}
