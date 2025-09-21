package co.com.crediya.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Loan count metric.")
public record LoanCountMetricDto(
        @Schema(name = "value", description = "Loan count value.", example = "50")
        Integer value,
        @Schema(name = "updated_at", description = "Timestamp of last update.", example = "2025-09-21T20:34:25Z")
        Instant updatedAt) {
}
