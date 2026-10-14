package com.corebridge.api.util;

import org.springframework.stereotype.Component;

@Component
public class ExternalIdNormalizer {

    public String normalize(String raw) {
        if (raw == null) {
            return null;
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        return trimmed.toUpperCase().replaceAll("\\s+", " ");
    }
}
