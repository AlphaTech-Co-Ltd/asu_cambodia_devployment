package com.asu_cambodia.asu.dto;

public record ErrorResponse(
        String message,
        String timestamp,
        int status
) {
}