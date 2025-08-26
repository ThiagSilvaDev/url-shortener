package com.shortit.urlshortener.controller;

import com.shortit.urlshortener.entity.Url;
import com.shortit.urlshortener.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shortener")
    public ResponseEntity<String> shortenerUrl(@RequestBody LongUrlRequest request) {
        Url url = urlService.generateShortUrl(request.getLongUrl());

        return ResponseEntity.ok("http://localhost:8080/" + url.getShortUrl());
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl) {
        String longUrl = urlService.getLongUrl(shortUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(longUrl));
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}

class LongUrlRequest {
    private String longUrl;

    public String getLongUrl() {
        return longUrl;
    }

}