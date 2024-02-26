package com.tsypk.urlshortener;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<ShortenedUrl, Long> {
    Optional<ShortenedUrl> findByShortCode();
    // Метод для подсчета сокращенных URL, которым не назначен оригинальный URL
    @Query("SELECT COUNT(u) FROM ShortenedUrl u WHERE u.originalUrl IS NULL")
    long countByOriginalUrlIsNull();
}
