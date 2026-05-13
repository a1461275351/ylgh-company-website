package com.ylguohe.admin.repository;

import com.ylguohe.admin.entity.LogisticsRoute;
import com.ylguohe.admin.entity.enums.LogisticsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogisticsRouteRepository extends JpaRepository<LogisticsRoute, Long> {
    List<LogisticsRoute> findByTypeAndActiveTrueOrderBySortOrder(LogisticsType type);
    List<LogisticsRoute> findByActiveTrueOrderBySortOrder();
    List<LogisticsRoute> findAllByOrderBySortOrder();
    List<LogisticsRoute> findByTypeOrderBySortOrder(LogisticsType type);
}
