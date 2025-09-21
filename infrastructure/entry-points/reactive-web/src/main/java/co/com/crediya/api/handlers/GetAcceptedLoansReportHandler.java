package co.com.crediya.api.handlers;

import co.com.crediya.api.mappers.AcceptedLoansReportDtoMapper;
import co.com.crediya.usecase.getacceptedloansreport.GetAcceptedLoansReportUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Log4j2
public class GetAcceptedLoansReportHandler {

    private final GetAcceptedLoansReportUseCase useCase;
    private final AcceptedLoansReportDtoMapper dtoMapper;

    @SuppressWarnings("unused")
    public Mono<ServerResponse> listenGetAcceptedLoansReport(ServerRequest serverRequest) {
        return this.useCase.execute()
                .map(this.dtoMapper::toResponseDto)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response)
                )
                .doFirst(() -> log.info("New request to get the accepted loans report."))
                .doOnSuccess(success -> log.info("Accepted loans report retrieved successfully."))
                .doOnError(err -> log.error("Error retrieving the accepted loans report.", err));
    }
}
