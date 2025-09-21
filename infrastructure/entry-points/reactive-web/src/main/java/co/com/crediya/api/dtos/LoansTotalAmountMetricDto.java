package co.com.crediya.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;

@Schema(description = "Loans total amount metric.")
public record LoansTotalAmountMetricDto(
        @Schema(name = "value", description = "Loans total amount value", example = "2500.50")
        BigDecimal value,
        @Schema(name = "updated_at", description = "Timestamp of last update.", example = "2025-09-21T20:34:25Z")
        Instant updatedAt
) {
}
