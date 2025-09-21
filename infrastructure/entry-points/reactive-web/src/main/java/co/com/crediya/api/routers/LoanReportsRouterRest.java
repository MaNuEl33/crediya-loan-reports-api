package co.com.crediya.api.routers;

import co.com.crediya.api.config.LoanReportsPath;
import co.com.crediya.api.handlers.GetAcceptedLoansReportHandler;
import co.com.crediya.api.handlers.GlobalErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class LoanReportsRouterRest {

    private final GetAcceptedLoansReportHandler getAcceptedLoansReportHandler;
    private final LoanReportsPath loanReportsPath;

    @Bean
    public RouterFunction<ServerResponse> loanReportsRoutes() {
        return route(GET(this.loanReportsPath.getAcceptedLoansReport()), this.getAcceptedLoansReportHandler::listenGetAcceptedLoansReport)
                .filter(GlobalErrorHandler.errorHandler());
    }
}
