package com.tsypk.urlshortener;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ShortenedUrls")
@Getter
@Setter
public class UrlMapping {

    @Id
    @Column(name = "short_code", nullable = false, length = 20)
    private String shortCode;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "access_count", nullable = false)
    private int accessCount = 0;

}

