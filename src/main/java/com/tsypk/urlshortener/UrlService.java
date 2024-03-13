package com.tsypk.urlshortener;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final BaseConversion conversion;

    public UrlService(UrlRepository urlRepository, BaseConversion baseConversion) {
        this.urlRepository = urlRepository;
        this.conversion = baseConversion;
    }

    public String convertToShortUrl(UrlLongRequest request) {
        Url url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setExpiresDate(request.getExpiresDate());
        url.setCreatedDate(new Date());
        Url entity = urlRepository.save(url);

        long createdDate = entity.getCreatedDate().getTime();
        int powLength = String.valueOf(createdDate).length();
        long shortId = (entity.getId() * (long) Math.pow(10, powLength)) + createdDate;
        String shortUrl = conversion.encode(shortId);
        entity.setShortUrl(shortUrl);
        return shortUrl;
    }

    public String getOriginalUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl);
        if (url == null) {
            throw new RuntimeException("Short URL not found");
        }
        return url.getLongUrl();
    }
}
