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

    private static final int DESIRED_SHORT_URL_COUNT = 1000;
    private final UrlMappingRepository urlMappingRepository;

    @Autowired
    public ShortUrlGenerator(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    @Scheduled(fixedDelay = 60000)
    public void generateShortUrls() {
        long count = urlMappingRepository.findByShortCodeIsNull();
        long shortUrlsToGenerate = DESIRED_SHORT_URL_COUNT - count;

        if (shortUrlsToGenerate > 0) {
            for (int i = 0; i < shortUrlsToGenerate; i++) {
                ShortenedUrl shortUrl = new ShortenedUrl();
                shortUrl.setShortCode(UUID.randomUUID().toString());
                shortUrl.setCreatedAt(LocalDateTime.now());
                urlMappingRepository.save(shortUrl);
            }
        }
    }
}

