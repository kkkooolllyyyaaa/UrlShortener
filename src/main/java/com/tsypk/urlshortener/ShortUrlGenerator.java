package com.tsypk.urlshortener;

import com.tsypk.urlshortener.ShortenedUrl;
import com.tsypk.urlshortener.UrlMappingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.tsypk.urlshortener.ShortenedUrl;
import com.tsypk.urlshortener.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.IntStream;


@Service
public class ShortUrlGenerator {

    private final UrlMappingRepository urlMappingRepository;
    private final SequenceService sequenceService; // Внедряем SequenceService

    @Autowired
    public ShortUrlGenerator(UrlMappingRepository urlMappingRepository, SequenceService sequenceService) {
        this.urlMappingRepository = urlMappingRepository;
        this.sequenceService = sequenceService;
    }

    @Scheduled(fixedDelay = 60000)
    public void generateShortUrls() {
        long count = urlMappingRepository.countByOriginalUrlIsNull();
        long shortUrlsToGenerate = DESIRED_SHORT_URL_COUNT - count;

        if (shortUrlsToGenerate > 0) {
            for (int i = 0; i < shortUrlsToGenerate; i++) {
                ShortenedUrl shortUrl = new ShortenedUrl();
                // Используем SequenceService для генерации уникального короткого кода
                String shortCode = sequenceService.generateNextShortCode();
                shortUrl.setShortCode(shortCode);
                shortUrl.setCreatedAt(LocalDateTime.now());
                // Поскольку originalUrl в данном контексте ещё не определён, его можно оставить пустым или задать значение по умолчанию
                urlMappingRepository.save(shortUrl);
            }
        }
    }
    private static final long DESIRED_SHORT_URL_COUNT = 100; // Примерное желаемое количество готовых коротких URL
}

