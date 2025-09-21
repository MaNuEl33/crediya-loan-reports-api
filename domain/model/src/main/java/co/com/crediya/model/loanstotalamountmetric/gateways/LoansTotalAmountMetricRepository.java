package co.com.crediya.model.loanstotalamountmetric.gateways;

import co.com.crediya.model.loanstotalamountmetric.LoansTotalAmountMetric;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface LoansTotalAmountMetricRepository {
    Mono<LoansTotalAmountMetric> incrementTotal(BigDecimal amount);
    Mono<LoansTotalAmountMetric> getMetric();
}
