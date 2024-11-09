package br.dev.zancanela.quickcup_api.dto.api.response;

import java.time.Instant;

public record ApiErrorDto(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
