package com.ylguohe.admin.controller.api;

import com.ylguohe.admin.entity.HeroBanner;
import com.ylguohe.admin.entity.Statistic;
import com.ylguohe.admin.service.HeroBannerService;
import com.ylguohe.admin.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homepage")
@RequiredArgsConstructor
public class HomepageApiController {

    private final HeroBannerService heroBannerService;
    private final StatisticService statisticService;

    @GetMapping("/banners")
    public ResponseEntity<List<HeroBanner>> getBanners() {
        return ResponseEntity.ok(heroBannerService.findAllActive());
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<Statistic>> getStatistics() {
        return ResponseEntity.ok(statisticService.findBySection("numbers"));
    }

    @GetMapping("/growth")
    public ResponseEntity<List<Statistic>> getGrowth() {
        return ResponseEntity.ok(statisticService.findBySection("growth"));
    }
}
