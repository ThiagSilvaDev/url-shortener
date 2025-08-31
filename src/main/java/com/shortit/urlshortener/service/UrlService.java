package com.shortit.urlshortener.service;

import com.google.common.hash.Hashing;
import com.shortit.urlshortener.dto.LongUrlRequest;
import com.shortit.urlshortener.entity.Url;
import com.shortit.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url generateShortUrl(LongUrlRequest request) {
        String longUrl = request.longUrl();

        if (longUrl == null || longUrl.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        Optional<Url> existingUrl = urlRepository.findByLongUrl(longUrl);

        if (existingUrl.isPresent()) {
            return existingUrl.get();
        }

        String hash = Hashing.murmur3_32_fixed().hashString(longUrl, StandardCharsets.UTF_8).toString();

        Url newUrl = new Url();
        newUrl.setShortUrl(hash);
        newUrl.setLongUrl(longUrl);

        return urlRepository.save(newUrl);
    }
    public String getLongUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl).orElse(null);
        
        return url != null ? url.getLongUrl() : null;
    }
}
