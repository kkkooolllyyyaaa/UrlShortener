package com.tsypk.urlshortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlShorteningService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    public String createShortUrl(String originalUrl) {
        // Логика для генерации короткого хэша
        String shortUrlHash = generateShortUrlHash();
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrlHash(shortUrlHash);
        urlMappingRepository.save(urlMapping);
        return shortUrlHash;
    }

    public Optional<String> getOriginalUrl(String shortUrlHash) {
        return Optional.ofNullable(urlMappingRepository.findByShortUrlHash(shortUrlHash))
                .map(UrlMapping::getOriginalUrl);
    }

    private String generateShortUrlHash() {
        // Примерная реализация генерации хэша
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }
}

