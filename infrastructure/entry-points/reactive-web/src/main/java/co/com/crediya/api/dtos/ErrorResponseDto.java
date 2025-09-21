package co.com.crediya.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Error response")
public record ErrorResponseDto(
        @Schema(description = "Error code.", example = "GE_001")
        String code,
        @Schema(description = "Error message", example = "Unexpected error.")
        String message,
        @Schema(description = "Error timestamp", example = "2025-09-20T20:34:25Z")
        Instant timestamp
) {
}
