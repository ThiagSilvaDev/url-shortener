package com.shortit.urlshortener.exception;

import com.shortit.urlshortener.dto.CustomErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleUrlNotFoundException(UrlNotFoundException ex, WebRequest request) {
        log.warn("URL not found exception: {}", ex.getMessage());
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UrlValidationException.class)
    public ResponseEntity<Object> handleUrlValidationException(UrlValidationException ex, WebRequest request) {
        log.warn("URL not valid exception: {}", ex.getMessage());
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UrlAlreadyExistException.class)
    public ResponseEntity<Object> handleUrlAlreadyExistException(UrlAlreadyExistException ex, WebRequest request) {
        log.info("URL already exists exception: {}", ex.getMessage());
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(customErrorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UrlShortenerException.class)
    public ResponseEntity<Object> handleUrlShortenerException(UrlShortenerException ex, WebRequest request) {
        log.error("URL shortener exception: {}", ex.getMessage());
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
