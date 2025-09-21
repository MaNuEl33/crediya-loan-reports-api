package co.com.crediya.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Accepted loans report")
public record AcceptedLoansReportDto(
        LoanCountMetricDto loanCount,
        LoansTotalAmountMetricDto loansTotalAmount
) {
}
