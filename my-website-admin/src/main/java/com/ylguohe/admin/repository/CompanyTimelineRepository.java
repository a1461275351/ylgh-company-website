package com.ylguohe.admin.repository;

import com.ylguohe.admin.entity.CompanyTimeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyTimelineRepository extends JpaRepository<CompanyTimeline, Long> {
    List<CompanyTimeline> findAllByOrderBySortOrder();
}
