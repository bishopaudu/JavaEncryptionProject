package com.jobDemo.workDemo.Helpers;

import java.util.UUID;

public class GenerateRandomKey {
    private String generateRandomKey() {
        // Implement logic to generate a random key (e.g., using SecureRandom)
        // For example purposes, you might use a simple generation like UUID
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
