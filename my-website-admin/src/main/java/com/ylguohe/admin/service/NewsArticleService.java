package com.ylguohe.admin.service;

import com.ylguohe.admin.entity.NewsArticle;
import com.ylguohe.admin.entity.enums.NewsCategory;
import com.ylguohe.admin.exception.ResourceNotFoundException;
import com.ylguohe.admin.repository.NewsArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsArticleService {

    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{4})(?:\\D+(\\d{1,2}))?(?:\\D+(\\d{1,2}))?");

    private final NewsArticleRepository repository;

    public List<NewsArticle> findAll() {
        return sortLatestFirst(repository.findAll());
    }

    public List<NewsArticle> findAllActive() {
        return sortLatestFirst(repository.findByActiveTrueOrderByCreatedAtDesc());
    }

    public List<NewsArticle> findByCategory(NewsCategory category) {
        return sortLatestFirst(repository.findByCategoryAndActiveTrueOrderByCreatedAtDesc(category));
    }

    public Optional<NewsArticle> findFeatured() {
        return repository.findByFeaturedTrueAndActiveTrue();
    }

    public List<NewsArticle> findLatest() {
        return findAllActive().stream().limit(3).collect(Collectors.toList());
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

    private List<NewsArticle> sortLatestFirst(List<NewsArticle> articles) {
        return articles.stream()
                .sorted(this::compareLatestFirst)
                .collect(Collectors.toList());
    }

    private int compareLatestFirst(NewsArticle a, NewsArticle b) {
        int result = Long.compare(sortKey(b), sortKey(a));
        if (result != 0) return result;

        result = Long.compare(nullToZero(b.getId()), nullToZero(a.getId()));
        if (result != 0) return result;

        return 0;
    }

    private long sortKey(NewsArticle article) {
        long dateKey = parseDateKey(article.getDate());
        if (dateKey > 0) return dateKey;

        long createdKey = toDateTimeKey(article.getCreatedAt());
        if (createdKey > 0) return createdKey;

        long updatedKey = toDateTimeKey(article.getUpdatedAt());
        if (updatedKey > 0) return updatedKey;

        return nullToZero(article.getId());
    }

    private long parseDateKey(String value) {
        if (value == null) return 0;

        Matcher matcher = DATE_PATTERN.matcher(value);
        if (!matcher.find()) return 0;

        int year = parseInt(matcher.group(1), 0);
        int month = parseInt(matcher.group(2), 1);
        int day = parseInt(matcher.group(3), 1);

        if (year <= 0 || month < 1 || month > 12 || day < 1 || day > 31) return 0;
        return year * 10000000000L + month * 100000000L + day * 1000000L;
    }

    private long toDateTimeKey(LocalDateTime value) {
        if (value == null) return 0;
        return value.getYear() * 10000000000L
                + value.getMonthValue() * 100000000L
                + value.getDayOfMonth() * 1000000L
                + value.getHour() * 10000L
                + value.getMinute() * 100L
                + value.getSecond();
    }

    private int parseInt(String value, int fallback) {
        if (value == null) return fallback;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    private long nullToZero(Long value) {
        return value == null ? 0 : value;
    }
}
