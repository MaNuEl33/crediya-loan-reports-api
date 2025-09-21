package co.com.crediya.usecase.updateacceptedloansreport;

import co.com.crediya.model.acceptedloansreport.AcceptedLoansReport;
import co.com.crediya.model.loancountmetric.LoanCountMetric;
import co.com.crediya.model.loancountmetric.gateways.LoanCountMetricRepository;
import co.com.crediya.model.loanstotalamountmetric.LoansTotalAmountMetric;
import co.com.crediya.model.loanstotalamountmetric.gateways.LoansTotalAmountMetricRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;

@ExtendWith(MockitoExtension.class)
class UpdateAcceptedLoansReportUseCaseTest {

    @Mock
    private LoanCountMetricRepository loanCountMetricRepository;

    @Mock
    private LoansTotalAmountMetricRepository loansTotalAmountMetricRepository;

    @InjectMocks
    private UpdateAcceptedLoansReportUseCase useCase;

    @Test
    void shouldUpdatedAcceptedLoansReportSuccessfully() {
        Mockito.when(this.loanCountMetricRepository.incrementCounter(Mockito.anyInt()))
                .thenReturn(Mono.just(LoanCountMetric.builder()
                        .value(2)
                        .updatedAt(Instant.parse("2025-09-21T20:34:25Z"))
                        .build()));

        Mockito.when(this.loansTotalAmountMetricRepository.incrementTotal(Mockito.any(BigDecimal.class)))
                .thenReturn(Mono.just(LoansTotalAmountMetric.builder()
                        .value(BigDecimal.valueOf(2000.50))
                        .updatedAt(Instant.parse("2025-09-21T20:36:25Z"))
                        .build()));

        final var expectedAcceptedLoansReport = AcceptedLoansReport.builder()
                .loanCount(LoanCountMetric.builder()
                        .value(2)
                        .updatedAt(Instant.parse("2025-09-21T20:34:25Z"))
                        .build())
                .loansTotalAmount(LoansTotalAmountMetric.builder()
                        .value(BigDecimal.valueOf(2000.50))
                        .updatedAt(Instant.parse("2025-09-21T20:36:25Z"))
                        .build())
                .build();

        StepVerifier.create(this.useCase.execute(BigDecimal.valueOf(150.50)))
                .expectNext(expectedAcceptedLoansReport)
                .verifyComplete();

        Mockito.verify(this.loanCountMetricRepository).incrementCounter(1);
        Mockito.verify(this.loansTotalAmountMetricRepository).incrementTotal(BigDecimal.valueOf(150.50));

        Mockito.verifyNoMoreInteractions(this.loanCountMetricRepository, this.loansTotalAmountMetricRepository);
    }
}
