package com.dakinnir.urlshortenerservice.service;

import com.dakinnir.urlshortenerservice.repository.URLRepository;
import com.dakinnir.urlshortenerservice.dto.URLShortenRequest;
import com.dakinnir.urlshortenerservice.dto.URLShortenResponse;
import com.dakinnir.urlshortenerservice.model.URL;
import com.dakinnir.urlshortenerservice.utils.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class URLShortenerService {

    private final URLRepository urlRepository;

    @Value("${app.base-url}")
    private String baseUrl;

     public URLShortenResponse createShortURL(URLShortenRequest request) {
         String generatedCode = generateShortCode(request);
         long ttlSeconds = request.getTtlSeconds() != null ? request.getTtlSeconds() : 2_592_000L; // 30 days
         Instant expiresAt = Instant.now().plusSeconds(ttlSeconds);

         URL url = new URL(generatedCode, request.getUrl(), expiresAt);
         urlRepository.save(url);
         return new URLShortenResponse(baseUrl + "/" + generatedCode, generatedCode, expiresAt);
     }

    private String generateShortCode(URLShortenRequest request) {
        // User provided alias to be used as short code. Validate and use it if valid.
        if (request.getAlias() != null && !request.getAlias().isBlank() && request.getAlias().matches("^[a-zA-Z0-9]{7}$")) {
            if (urlRepository.existsByShortCode(request.getAlias())) {
                throw new IllegalArgumentException("Alias already taken: " + request.getAlias());
            }
            return request.getAlias();
        }
        // Generate a unique short code using Base62 encoding. Retry if collision occurs.
        // Throw a runtime exception if we fail to generate a unique code after 5 attempts.
        String code;
        int attempts = 0;
        do {
            code = Base62Encoder.generate();
            if (++attempts > 5) throw new RuntimeException("Failed to generate unique code after multiple attempts");
        } while (urlRepository.existsByShortCode(code));
        return code;
    }

    public String getOriginalLongURL(String code) {
        URL url = urlRepository.findByShortCode(code);
        if (url == null) {
            throw new IllegalArgumentException("Short code not found: " + code);
        }
        if (url.isExpired()) {
            throw new IllegalStateException("Short URL has expired: " + code);
        }
        url.setClicksCount(url.getClicksCount() + 1);
        urlRepository.save(url);
        return url.getLongURL();
    }
}
