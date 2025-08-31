package com.shortit.urlshortener.controller;

import com.shortit.urlshortener.dto.LongUrlRequest;
import com.shortit.urlshortener.dto.ShortUrlResponse;
import com.shortit.urlshortener.entity.Url;
import com.shortit.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shortener")
    public ResponseEntity<String> shortenerUrl(@Valid @RequestBody LongUrlRequest request) {
        Url url = urlService.generateShortUrl(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{shortUrl}")
                .buildAndExpand(url.getShortUrl())
                .toUri();

        ShortUrlResponse response = new ShortUrlResponse(url.getShortUrl());

        return ResponseEntity.created(location).body("http://localhost:8080/" + response.shortUrl());
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl) {
        String longUrl = urlService.getLongUrl(shortUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(longUrl));
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}