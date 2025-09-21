package co.com.crediya.model.loancountmetric;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
public class LoanCountMetric {
    public static final String CODE = "loan_count";

    private Integer value;
    private Instant updatedAt;
}
