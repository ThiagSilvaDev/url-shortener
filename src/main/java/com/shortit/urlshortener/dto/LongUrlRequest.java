package com.shortit.urlshortener.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LongUrlRequest(
        @NotNull
        @NotEmpty(message = "URL cannot be empty")
        String longUrl
) {
    public LongUrlRequest(String longUrl) {
        this.longUrl = longUrl;
    }
}

