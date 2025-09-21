package co.com.crediya.usecase.updateacceptedloansreport;

import co.com.crediya.model.acceptedloansreport.AcceptedLoansReport;
import co.com.crediya.model.loancountmetric.gateways.LoanCountMetricRepository;
import co.com.crediya.model.loanstotalamountmetric.gateways.LoansTotalAmountMetricRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class UpdateAcceptedLoansReportUseCase {

    private final LoanCountMetricRepository loanCountMetricRepository;
    private final LoansTotalAmountMetricRepository loansTotalAmountMetricRepository;

    public Mono<AcceptedLoansReport> execute(BigDecimal amount) {
        return Mono.zip(this.loanCountMetricRepository.incrementCounter(1),
                        this.loansTotalAmountMetricRepository.incrementTotal(amount))
                .map(tuple -> AcceptedLoansReport.builder()
                        .loanCount(tuple.getT1())
                        .loansTotalAmount(tuple.getT2())
                        .build());
    }
}
