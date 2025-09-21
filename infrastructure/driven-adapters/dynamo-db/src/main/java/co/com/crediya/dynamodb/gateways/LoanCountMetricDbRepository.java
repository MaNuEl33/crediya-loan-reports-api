package co.com.crediya.dynamodb.gateways;

import co.com.crediya.dynamodb.templates.LoanCountMetricTemplateAdapter;
import co.com.crediya.model.loancountmetric.LoanCountMetric;
import co.com.crediya.model.loancountmetric.gateways.LoanCountMetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Repository
@Log4j2
@RequiredArgsConstructor
public class LoanCountMetricDbRepository implements LoanCountMetricRepository {

    private final LoanCountMetricTemplateAdapter templateAdapter;

    @Override
    public Mono<LoanCountMetric> incrementCounter(Integer increment) {
        return this.templateAdapter.getById(LoanCountMetric.CODE)
                .defaultIfEmpty(LoanCountMetric.builder()
                        .value(0)
                        .build())
                .flatMap(retrieved -> this.templateAdapter.save(LoanCountMetric.builder()
                        .value(retrieved.getValue() + increment)
                        .updatedAt(Instant.now())
                        .build()))
                .doFirst(() -> log.info("The loan account metric will be increment in {}", increment))
                .doOnSuccess(success -> log.info("The loan account metric was incremented successfully."))
                .doOnError(err -> log.error("There was an error incrementing the loan account metric.", err));
    }

    @Override
    public Mono<LoanCountMetric> getMetric() {
        return this.templateAdapter.getById(LoanCountMetric.CODE)
                .defaultIfEmpty(LoanCountMetric.builder()
                        .value(0)
                        .updatedAt(Instant.now())
                        .build())
                .doFirst(() -> log.info("Retrieving the loan count metric."))
                .doOnSuccess(success -> log.info("The loan count metric was retrieved successfully."))
                .doOnError(err -> log.error("There was an error retrieving the loan count metric.", err));
    }
}
