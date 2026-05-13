package com.ylguohe.admin.service;

import com.ylguohe.admin.entity.NewsArticle;
import com.ylguohe.admin.entity.enums.NewsCategory;
import com.ylguohe.admin.exception.ResourceNotFoundException;
import com.ylguohe.admin.repository.NewsArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsArticleService {

    private final NewsArticleRepository repository;

    public List<NewsArticle> findAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    public List<NewsArticle> findAllActive() {
        return repository.findByActiveTrueOrderByCreatedAtDesc();
    }

    public List<NewsArticle> findByCategory(NewsCategory category) {
        return repository.findByCategoryAndActiveTrueOrderByCreatedAtDesc(category);
    }

    public Optional<NewsArticle> findFeatured() {
        return repository.findByFeaturedTrueAndActiveTrue();
    }

    public List<NewsArticle> findLatest() {
        return repository.findTop3ByActiveTrueOrderByCreatedAtDesc();
    }

    public NewsArticle findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NewsArticle", id));
    }

    public NewsArticle save(NewsArticle article) {
        return repository.save(article);
    }

    @Transactional
    public void toggleFeatured(Long id) {
        NewsArticle article = findById(id);
        if (!article.getFeatured()) {
            // Unset any existing featured article
            repository.findByFeaturedTrueAndActiveTrue().ifPresent(existing -> {
                existing.setFeatured(false);
                repository.save(existing);
            });
        }
        article.setFeatured(!article.getFeatured());
        repository.save(article);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }
}
