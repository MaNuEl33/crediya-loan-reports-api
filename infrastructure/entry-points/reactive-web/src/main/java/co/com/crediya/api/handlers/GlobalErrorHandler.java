package co.com.crediya.api.handlers;

import co.com.crediya.api.dtos.ErrorResponseDto;
import co.com.crediya.api.enums.ReactiveWebError;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;

@UtilityClass
public class GlobalErrorHandler {

    public static HandlerFilterFunction<ServerResponse, ServerResponse> errorHandler() {
        return (request, next) ->next.handle(request)
                .onErrorResume(Exception.class, GlobalErrorHandler::handleUnexpectedException);
    }

    private static Mono<ServerResponse> handleUnexpectedException(Exception e) {
        final var errorResponse = new ErrorResponseDto(ReactiveWebError.GENERIC_ERROR.getCode(),
                ReactiveWebError.GENERIC_ERROR.getMessage(), Instant.now());

        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse);
    }
}
