package org.example.information_security.dto.responce;

public record ErrorResponseDto(
        int status,
        String error,
        String message
) {}