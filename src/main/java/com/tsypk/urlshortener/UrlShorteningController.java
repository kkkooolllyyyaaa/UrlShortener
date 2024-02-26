package com.tsypk.urlshortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UrlShorteningController {

    private final UrlShorteningService urlShorteningService;

    @Autowired
    public UrlShorteningController(UrlShorteningService urlShorteningService) {
        this.urlShorteningService = urlShorteningService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest request) {
        return urlShorteningService.allocateShortUrl(request.getUrl())
                .map(shortenedUrl -> ResponseEntity.ok(new ShortenUrlResponse(shortenedUrl.getShortCode())))
                .orElse(ResponseEntity.badRequest().body(new ShortenUrlResponse("Error: Could not allocate a short URL.")));
    }

    @GetMapping("/{shortUrlHash}")
    public ResponseEntity<?> redirect(@PathVariable String shortUrlHash) {
        return urlShorteningService.getOriginalUrl(shortUrlHash)
                .map(url -> ResponseEntity.status(307).header("Location", url).build())
                .orElse(ResponseEntity.notFound().build());
    }
}

