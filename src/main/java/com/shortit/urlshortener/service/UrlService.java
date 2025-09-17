package com.shortit.urlshortener.service;

import com.google.common.hash.Hashing;
import com.shortit.urlshortener.entity.Url;
import com.shortit.urlshortener.exception.UrlNotFoundException;
import com.shortit.urlshortener.exception.UrlValidationException;
import com.shortit.urlshortener.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

@Service
public final class UrlService {

    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url generateShortUrl(String longUrl) {
        if (!StringUtils.hasText(longUrl)) {
            throw new UrlValidationException("URL cannot be null or empty");
        }

        return urlRepository.findByLongUrl(longUrl)
                .orElseGet(() -> {
                    logger.info("Generating long URL: {}", longUrl);
                    return createAndSaveUrl(longUrl);
                });
    }

    public String getLongUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl)
                .map(Url::getLongUrl)
                .orElseThrow(() -> new UrlNotFoundException("URL not found: " + shortUrl));
    }

    private Url createAndSaveUrl(String longUrl) {
        // TODO improve the hashing algorithm to avoid collisions
        String hash = Hashing.murmur3_32_fixed().hashString(longUrl, StandardCharsets.UTF_8).toString();

        Url newUrl = new Url();
        newUrl.setShortUrl(hash);
        newUrl.setLongUrl(longUrl);

        return urlRepository.save(newUrl);
    }
}
