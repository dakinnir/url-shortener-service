package com.dakinnir.urlshortenerservice.utils;

import java.security.SecureRandom;

public class Base62Encoder {
    // 62 characters^7 possible combinations prevent collisions for a large number of URLs
    private static final String CHARACTERS =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 7;

    public static String generate() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(62)));
        }
        return sb.toString();
    }
}