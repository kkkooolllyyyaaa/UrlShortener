package com.tsypk.urlshortener;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "shortened_urls")
@Getter
@Setter
public class ShortenedUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_code", nullable = false, length = 20)
    private String shortCode;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "access_count", nullable = false)
    private int accessCount;

    // Метод для увеличения счетчика доступа
    public void incrementAccessCount() {
        this.accessCount++;
    }

    // Метод для проверки, истек ли срок действия URL
    public boolean isExpired() {
        return expiredAt != null && LocalDateTime.now().isAfter(expiredAt);
    }

    // Метод для установки срока действия, исходя из заданного TTL
    public void setTTL(long ttlInSeconds) {
        this.expiredAt = this.createdAt.plusSeconds(ttlInSeconds);
    }
}

