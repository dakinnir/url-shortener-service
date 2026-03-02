package com.dakinnir.urlshortenerservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@Entity
@RequiredArgsConstructor
public class URL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // columnDefinition in postgreSQL to store long URLs.
    @Column(name = "long_url", nullable = false, columnDefinition = "TEXT")
    private String longURL;

    @Column(name = "short_code", unique = true, nullable = false, length = 7)
    private String shortCode;

    @Column(name = "clicks", nullable = false)
    private long clicksCount;

    // Instant: Represents a specific moment on the timeline, independent of any time zone.
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "expires_at", nullable = false, updatable = false)
    private Instant expiresAt;

    public URL(String shortCode, String longURL, Instant expiresAt) {
        this.shortCode = shortCode;
        this.longURL = longURL;
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }
}