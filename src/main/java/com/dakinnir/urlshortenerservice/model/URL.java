package com.dakinnir.urlshortenerservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
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

    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }
}