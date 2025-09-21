package co.com.crediya.usecase.getacceptedloansreport;

import co.com.crediya.model.acceptedloansreport.AcceptedLoansReport;
import co.com.crediya.model.loancountmetric.gateways.LoanCountMetricRepository;
import co.com.crediya.model.loanstotalamountmetric.gateways.LoansTotalAmountMetricRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetAcceptedLoansReportUseCase {

    private final LoanCountMetricRepository loanCountMetricRepository;
    private final LoansTotalAmountMetricRepository loansTotalAmountMetricRepository;

    public Mono<AcceptedLoansReport> execute() {
        return Mono.zip(this.loanCountMetricRepository.getMetric(), this.loansTotalAmountMetricRepository.getMetric())
                .map(tuple -> AcceptedLoansReport.builder()
                        .loanCount(tuple.getT1())
                        .loansTotalAmount(tuple.getT2())
                        .build());
    }
}
