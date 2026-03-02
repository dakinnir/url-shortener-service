package com.dakinnir.urlshortenerservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
public class URLShortenRequest {

    @NotBlank(message = "URL must not be blank")
    // For simplicity - using regex to validate that the URL starts with http:// or https://
    @Pattern(
            regexp = "^https?://.*",
            message = "URL must start with http:// or https://"
    )
    private String url;

    private String alias;
    private Long ttlSeconds;
}