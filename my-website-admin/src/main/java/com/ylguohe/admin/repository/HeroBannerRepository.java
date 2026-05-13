package com.ylguohe.admin.repository;

import com.ylguohe.admin.entity.HeroBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroBannerRepository extends JpaRepository<HeroBanner, Long> {
    List<HeroBanner> findByActiveTrueOrderBySortOrder();
    List<HeroBanner> findAllByOrderBySortOrder();
}
