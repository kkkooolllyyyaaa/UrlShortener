package com.tsypk.urlshortener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class UrlController {

    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @Operation(summary = "Convert new url", description = "Converts long url to short url")
    @PostMapping("/create-short")
    public ResponseEntity<String> convertToShortUrl(@RequestBody UrlLongRequest request) {
        logger.info("Received URL to shorten: {}", request.getLongUrl());
        try {
            String shortUrl = urlService.convertToShortUrl(request);
            return ResponseEntity.ok(shortUrl);
        } catch (Exception e) {
            logger.error("Error when converting to short url: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error when converting to short url");
        }
    }

    @Operation(summary = "Redirect", description = "Finds original url from short url and redirects")
    @GetMapping("/{shortUrl}")
    @Cacheable(value = "urls", key = "#shortUrl", sync = true)
    public ResponseEntity<Void> getAndRedirect(@PathVariable String shortUrl) {
        logger.info("Received short URL for redirection: {}", shortUrl);
        try {
            String originalUrl = urlService.getOriginalUrl(shortUrl);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(originalUrl))
                    .build();
        } catch (Exception e) {
            logger.error("Error during redirection for short url {}: {}", shortUrl, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}

