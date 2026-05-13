package com.ylguohe.admin.controller.api;

import com.ylguohe.admin.entity.NewsArticle;
import com.ylguohe.admin.entity.enums.NewsCategory;
import com.ylguohe.admin.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsApiController {

    private final NewsArticleService newsArticleService;

    @GetMapping
    public ResponseEntity<List<NewsArticle>> getNews(@RequestParam(required = false) String category) {
        if (category != null) {
            return ResponseEntity.ok(newsArticleService.findByCategory(NewsCategory.valueOf(category.toUpperCase())));
        }
        return ResponseEntity.ok(newsArticleService.findAllActive());
    }

    @GetMapping("/featured")
    public ResponseEntity<NewsArticle> getFeatured() {
        return newsArticleService.findFeatured()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/latest")
    public ResponseEntity<List<NewsArticle>> getLatest() {
        return ResponseEntity.ok(newsArticleService.findLatest());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsArticle> getNewsById(@PathVariable Long id) {
        return ResponseEntity.ok(newsArticleService.findById(id));
    }
}
