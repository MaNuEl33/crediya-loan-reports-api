package co.com.crediya.dynamodb.gateways;

import co.com.crediya.dynamodb.templates.LoansTotalAmountMetricTemplateAdapter;
import co.com.crediya.model.loanstotalamountmetric.LoansTotalAmountMetric;
import co.com.crediya.model.loanstotalamountmetric.gateways.LoansTotalAmountMetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;

@Repository
@Log4j2
@RequiredArgsConstructor
public class LoansTotalAmountMetricDbRepository implements LoansTotalAmountMetricRepository {

    private final LoansTotalAmountMetricTemplateAdapter templateAdapter;

    @Override
    public Mono<LoansTotalAmountMetric> incrementTotal(BigDecimal amount) {
        return this.templateAdapter.getById(LoansTotalAmountMetric.CODE)
                .defaultIfEmpty(LoansTotalAmountMetric.builder()
                        .value(BigDecimal.ZERO)
                        .build())
                .flatMap(retrieved -> this.templateAdapter.save(LoansTotalAmountMetric.builder()
                        .value(retrieved.getValue().add(amount))
                        .updatedAt(Instant.now())
                        .build()))
                .doFirst(() -> log.info("The loans total amount metric will be increment in {}", amount))
                .doOnSuccess(success -> log.info("The loans total amount metric was incremented successfully."))
                .doOnError(err -> log.error("There was an error incrementing the loans total amount metric.", err));
    }

    @Override
    public Mono<LoansTotalAmountMetric> getMetric() {
        return this.templateAdapter.getById(LoansTotalAmountMetric.CODE)
                .defaultIfEmpty(LoansTotalAmountMetric.builder()
                        .value(BigDecimal.ZERO)
                        .updatedAt(Instant.now())
                        .build())
                .doFirst(() -> log.info("Retrieving the loans total amount metric."))
                .doOnSuccess(success -> log.info("The loans total amount metric was retrieved successfully."))
                .doOnError(err -> log.error("There was an error retrieving the loans total amount metric.", err));
    }
}
