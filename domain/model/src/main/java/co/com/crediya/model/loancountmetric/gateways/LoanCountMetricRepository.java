package co.com.crediya.model.loancountmetric.gateways;

import co.com.crediya.model.loancountmetric.LoanCountMetric;
import reactor.core.publisher.Mono;

public interface LoanCountMetricRepository {
    Mono<LoanCountMetric> incrementCounter(Integer increment);
    Mono<LoanCountMetric> getMetric();
}
