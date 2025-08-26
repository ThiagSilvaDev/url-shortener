package com.shortit.urlshortener.service;

import com.google.common.hash.Hashing;
import com.shortit.urlshortener.entity.Url;
import com.shortit.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url generateShortUrl(String longUrl) {
        if (longUrl == null || longUrl.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        String hash = Hashing.murmur3_32_fixed().hashString(longUrl, StandardCharsets.UTF_8).toString();

        Url url = new Url();
        url.setShortUrl(hash);
        url.setLongUrl(longUrl);

        return urlRepository.save(url);
    }
    public String getLongUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl).orElse(null);
        
        return url != null ? url.getLongUrl() : null;
    }
}
