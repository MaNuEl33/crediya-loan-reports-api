package co.com.crediya.usecase.getacceptedloansreport;

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
class GetAcceptedLoansReportUseCaseTest {

    @Mock
    private LoanCountMetricRepository loanCountMetricRepository;

    @Mock
    private LoansTotalAmountMetricRepository loansTotalAmountMetricRepository;

    @InjectMocks
    private GetAcceptedLoansReportUseCase useCase;

    @Test
    void shouldGetAcceptedLoansReportSuccessfully() {
        Mockito.when(this.loanCountMetricRepository.getMetric())
                .thenReturn(Mono.just(LoanCountMetric.builder()
                        .value(2)
                        .updatedAt(Instant.parse("2025-09-21T20:34:25Z"))
                        .build()));

        Mockito.when(this.loansTotalAmountMetricRepository.getMetric())
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

        StepVerifier.create(this.useCase.execute())
                .expectNext(expectedAcceptedLoansReport)
                .verifyComplete();

        Mockito.verify(this.loanCountMetricRepository).getMetric();
        Mockito.verify(this.loansTotalAmountMetricRepository).getMetric();

        Mockito.verifyNoMoreInteractions(this.loanCountMetricRepository, this.loansTotalAmountMetricRepository);
    }
}
