package com.shortit.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record LongUrlRequest(
        @NotBlank
        @Size(max = 2048, message = "URL must not exceed 2048 characters")
        @URL(message = "URL must be valid")
        String longUrl
) {
}

