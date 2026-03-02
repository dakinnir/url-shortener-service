package com.dakinnir.urlshortenerservice.repository;

import com.dakinnir.urlshortenerservice.model.URL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URLRepository extends JpaRepository<URL, Long> {
    URL findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);
}

