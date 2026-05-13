package com.ylguohe.admin.controller.admin;

import com.ylguohe.admin.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/files")
@RequiredArgsConstructor
public class AdminFileController {

    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        String path = fileStorageService.store(file);
        Map<String, String> result = new HashMap<>();
        result.put("url", path);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String path) {
        fileStorageService.delete(path);
        return ResponseEntity.ok().build();
    }
}
