package com.ylguohe.admin.repository;

import com.ylguohe.admin.entity.NewsArticle;
import com.ylguohe.admin.entity.enums.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    List<NewsArticle> findByActiveTrueOrderByCreatedAtDesc();
    List<NewsArticle> findByCategoryAndActiveTrueOrderByCreatedAtDesc(NewsCategory category);
    Optional<NewsArticle> findByFeaturedTrueAndActiveTrue();
    List<NewsArticle> findTop3ByActiveTrueOrderByCreatedAtDesc();
    List<NewsArticle> findAllByOrderByCreatedAtDesc();
}
