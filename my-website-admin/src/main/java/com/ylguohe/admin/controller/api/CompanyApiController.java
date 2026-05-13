package com.ylguohe.admin.controller.api;

import com.ylguohe.admin.entity.CompanyTimeline;
import com.ylguohe.admin.entity.Qualification;
import com.ylguohe.admin.service.CompanyTimelineService;
import com.ylguohe.admin.service.QualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyApiController {

    private final CompanyTimelineService timelineService;
    private final QualificationService qualificationService;

    @GetMapping("/timeline")
    public ResponseEntity<List<CompanyTimeline>> getTimeline() {
        return ResponseEntity.ok(timelineService.findAll());
    }

    @GetMapping("/qualifications")
    public ResponseEntity<List<Qualification>> getQualifications() {
        return ResponseEntity.ok(qualificationService.findAllActive());
    }
}
