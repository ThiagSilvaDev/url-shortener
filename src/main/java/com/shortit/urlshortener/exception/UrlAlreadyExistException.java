package com.shortit.urlshortener.exception;

public class UrlAlreadyExistException extends  UrlShortenerException {

    public UrlAlreadyExistException(String message) {
        super(message);
    }
}
