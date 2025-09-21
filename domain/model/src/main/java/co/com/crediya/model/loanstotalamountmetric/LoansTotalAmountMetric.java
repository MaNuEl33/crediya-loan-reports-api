package co.com.crediya.model.loanstotalamountmetric;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
public class LoansTotalAmountMetric {
    public static final String CODE = "loans_total_amount";

    private BigDecimal value;
    private Instant updatedAt;
}
