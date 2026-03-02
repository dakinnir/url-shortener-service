package com.dakinnir.urlshortenerservice.controller;

import com.dakinnir.urlshortenerservice.dto.URLShortenRequest;
import com.dakinnir.urlshortenerservice.dto.URLShortenResponse;
import com.dakinnir.urlshortenerservice.service.URLShortenerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class URLController {

    private final URLShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public ResponseEntity<URLShortenResponse> shorten(@Valid @RequestBody URLShortenRequest request) {
        URLShortenResponse response = urlShortenerService.createShortURL(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {
        String longUrl = urlShortenerService.getOriginalLongURL(code);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, longUrl)
                .build();
    }

}