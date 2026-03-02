package com.dakinnir.urlshortenerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class URLShortenResponse {
    private String shortUrl;
    private String shortCode;
    private Instant expiresAt;
}