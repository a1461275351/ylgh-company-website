package com.ylguohe.admin.repository;

import com.ylguohe.admin.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    List<Statistic> findBySectionAndActiveTrueOrderBySortOrder(String section);
    List<Statistic> findByActiveTrueOrderBySortOrder();
    List<Statistic> findAllByOrderBySortOrder();
}
