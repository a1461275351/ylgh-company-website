package com.ylguohe.admin.repository;

import com.ylguohe.admin.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    List<Qualification> findByActiveTrueOrderBySortOrder();
    List<Qualification> findAllByOrderBySortOrder();
}
