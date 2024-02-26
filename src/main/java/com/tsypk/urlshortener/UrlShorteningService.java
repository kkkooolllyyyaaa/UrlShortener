package com.tsypk.urlshortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlShorteningService {

    private final UrlMappingRepository urlMappingRepository;

    @Autowired
    public UrlShorteningService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public Optional<ShortenedUrl> allocateShortUrl(String originalUrl) {
        Optional<ShortenedUrl> shortUrlOpt = urlMappingRepository.findByShortCode();
        if (shortUrlOpt.isPresent()) {
            ShortenedUrl shortUrl = shortUrlOpt.get();
            shortUrl.setOriginalUrl(originalUrl);
            shortUrl.setCreatedAt(LocalDateTime.now());
            urlMappingRepository.save(shortUrl);
            return Optional.of(shortUrl);
        }
        return
                Optional.empty();
    }
    public Optional<String> getOriginalUrl(String shortCode) {
        return urlMappingRepository.findById(shortCode)
                .map(ShortenedUrl::getOriginalUrl);
    }
}



