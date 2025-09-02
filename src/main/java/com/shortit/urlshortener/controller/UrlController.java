package com.shortit.urlshortener.controller;

import com.shortit.urlshortener.dto.LongUrlRequest;
import com.shortit.urlshortener.dto.ShortUrlResponse;
import com.shortit.urlshortener.entity.Url;
import com.shortit.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class UrlController {
    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

    private final UrlService urlService;

    @Value("${app.base-url}")
    private String baseUrl;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shortener")
    public ResponseEntity<ShortUrlResponse> shortenerUrl(@Valid @RequestBody LongUrlRequest request) {
        logger.info("Received URL shortening request for: {}", request.longUrl());

        Url url = urlService.generateShortUrl(request);

        // TODO change the approach to generate the location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{shortUrl}")
                .buildAndExpand(url.getShortUrl())
                .toUri();

        ShortUrlResponse response = new ShortUrlResponse(
                request.longUrl(),
                baseUrl + "/" + url.getShortUrl()
        );

        logger.info("Shortened URL: {} to {}", request.longUrl(), response.shortUrl());

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl) {
        logger.info("Received request to redirect short URL: {}", shortUrl);

        String longUrl = urlService.getLongUrl(shortUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(longUrl));

        logger.info("Redirecting to long URL: {}", longUrl);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).build();
    }
}