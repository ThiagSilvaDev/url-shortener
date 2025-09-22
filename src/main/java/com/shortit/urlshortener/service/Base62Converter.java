package com.shortit.urlshortener.service;

import org.springframework.stereotype.Component;

@Component
public class Base62Converter {

    private static final String BASE62_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int BASE = BASE62_CHARS.length();

    public String encode(long number) {
        if (number == 0) {
            return String.valueOf(BASE62_CHARS.charAt(0));
        }

        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            sb.append(BASE62_CHARS.charAt((int) (number % BASE)));
            number /= BASE;
        }

        return sb.reverse().toString();
    }
}
