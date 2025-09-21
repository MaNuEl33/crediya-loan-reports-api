package co.com.crediya.model.acceptedloansreport;

import co.com.crediya.model.loancountmetric.LoanCountMetric;
import co.com.crediya.model.loanstotalamountmetric.LoansTotalAmountMetric;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
public class AcceptedLoansReport {
    private LoanCountMetric loanCount;
    private LoansTotalAmountMetric loansTotalAmount;
}
