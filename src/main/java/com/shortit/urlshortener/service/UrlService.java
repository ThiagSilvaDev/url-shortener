package com.shortit.urlshortener.service;

import com.google.common.hash.Hashing;
import com.shortit.urlshortener.dto.LongUrlRequest;
import com.shortit.urlshortener.entity.Url;
import com.shortit.urlshortener.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UrlService {
    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url generateShortUrl(LongUrlRequest request) {
        String longUrl = request.longUrl();

        // TODO create a custom exception for this
        if (longUrl == null || longUrl.isEmpty()) {
            logger.error("URL is null or empty: {}", longUrl);
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        Optional<Url> existingUrl = urlRepository.findByLongUrl(longUrl);

        // TODO create a custom exception for this
        if (existingUrl.isPresent()) {
            logger.info("URL already exists: {}", existingUrl.get());
            return existingUrl.get();
        }

        // TODO improve the hashing algorithm to avoid collisions
        String hash = Hashing.murmur3_32_fixed().hashString(longUrl, StandardCharsets.UTF_8).toString();

        Url newUrl = new Url();
        newUrl.setShortUrl(hash);
        newUrl.setLongUrl(longUrl);

        return urlRepository.save(newUrl);
    }
    public String getLongUrl(String shortUrl) {
        // TODO create a custom exception for this
        // TODO change the approach to handle not found
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> {
            logger.warn("Short URL not found: {}", shortUrl);
            return new IllegalArgumentException("Short URL not found: " + shortUrl);
        });
        
        return url != null ? url.getLongUrl() : null;
    }
}
