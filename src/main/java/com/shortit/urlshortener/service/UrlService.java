package com.shortit.urlshortener.service;

import com.shortit.urlshortener.entity.Url;
import com.shortit.urlshortener.exception.UrlNotFoundException;
import com.shortit.urlshortener.exception.UrlValidationException;
import com.shortit.urlshortener.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UrlService {

    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);
    private final UrlRepository urlRepository;
    private final RedisTemplate<String, Url> redisTemplate;
    private final Base62Converter base62Converter;

    public UrlService(RedisTemplate<String, Url> redisTemplate, UrlRepository urlRepository, Base62Converter base62Converter) {
        this.redisTemplate = redisTemplate;
        this.urlRepository = urlRepository;
        this.base62Converter = base62Converter;
    }

    @Transactional
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

    @Transactional(readOnly = true)
    public String getLongUrl(String shortUrl) {
        logger.info("Getting long URL: {}", shortUrl);

        Url cacheUrl = getCachedUrl(shortUrl);
        if (cacheUrl != null) {
            return cacheUrl.getLongUrl();
        }

        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("Url not found" + shortUrl));

        cacheUrl(url);

        return url.getLongUrl();
    }

    private Url createAndSaveUrl(String longUrl) {
        logger.info("Creating new URL entity: {}", longUrl);
        Url newUrl = new Url();
        newUrl.setLongUrl(longUrl);

        Url savedUrl = urlRepository.save(newUrl);
        logger.info("Saved long URL: {}", savedUrl);

        String hash = base62Converter.encode(savedUrl.getId());
        logger.info("Encoded long URL: {} to {}", longUrl, hash);

        savedUrl.setShortUrl(hash);
        logger.info("Set short URL: {}", savedUrl.getShortUrl());

        return savedUrl;
    }

    private void cacheUrl(Url url) {
        logger.info("Caching URL: {}", url);

        redisTemplate.opsForValue().set(url.getShortUrl(), url);
    }

    private Url getCachedUrl(String shortUrl) {
        return redisTemplate.opsForValue().get(shortUrl);
    }
}
