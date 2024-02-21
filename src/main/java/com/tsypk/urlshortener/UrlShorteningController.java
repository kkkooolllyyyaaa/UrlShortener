package com.tsypk.urlshortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UrlShorteningController {

    @Autowired
    private UrlShorteningService urlShorteningService;

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        String shortUrlHash = urlShorteningService.createShortUrl(originalUrl);
        return ResponseEntity.ok(Map.of("shortUrl", "http://localhost:8080/" + shortUrlHash));
    }

    @GetMapping("/{shortUrlHash}")
    public ResponseEntity<?> redirect(@PathVariable String shortUrlHash) {
        return urlShorteningService.getOriginalUrl(shortUrlHash)
                .map(url -> ResponseEntity.status(307).header("Location", url).build())
                .orElse(ResponseEntity.notFound().build());
    }
}

