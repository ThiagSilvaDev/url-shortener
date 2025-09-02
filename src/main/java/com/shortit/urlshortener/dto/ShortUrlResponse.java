package com.shortit.urlshortener.dto;

public record ShortUrlResponse(
        // TODO improve the response
        String longUrl,
        String shortUrl
) {
}
