package co.com.crediya.api.config;

import co.com.crediya.api.dtos.ErrorResponseDto;
import co.com.crediya.api.enums.ReactiveWebError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@Log4j2
public class HttpSecurityConfig {

    private final ReactiveAuthenticationManager authenticationManager;
    private final ServerAuthenticationConverter serverAuthenticationConverter;
    private final LoanReportsPath loanReportsPath;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        final var jwtAuthFilter = this.buildAuthenticationWebFilter();

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(this.loanReportsPath.getAcceptedLoansReport()).hasRole("ADMIN")
                        .pathMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(this::handleAccessDeniedError)
                )
                .build();
    }

    private AuthenticationWebFilter buildAuthenticationWebFilter() {
        final var jwtAuthFilter = new AuthenticationWebFilter(this.authenticationManager);

        jwtAuthFilter.setServerAuthenticationConverter(this.serverAuthenticationConverter);
        jwtAuthFilter.setAuthenticationFailureHandler((wex, e) ->
                this.handleAuthenticationFailureError(wex.getExchange(), e));

        return jwtAuthFilter;
    }

    private Mono<Void> handleAuthenticationFailureError(ServerWebExchange exchange, AuthenticationException e) {
        try {
            log.error("Authentication Failure Error.", e);

            final var errorResponse = new ErrorResponseDto(ReactiveWebError.INVALID_TOKEN.getCode(),
                    ReactiveWebError.INVALID_TOKEN.getMessage(), Instant.now());
            final var bytes = this.objectMapper.writeValueAsBytes(errorResponse);

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return exchange.getResponse().writeWith(
                    Mono.just(exchange.getResponse().bufferFactory().wrap(bytes))
            );
        } catch (JsonProcessingException ex) {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
        }
    }

    private Mono<Void> handleAccessDeniedError(ServerWebExchange exchange, AccessDeniedException e) {
        try {
            log.error("Access Denied Error.", e);

            final var errorResponse = new ErrorResponseDto(ReactiveWebError.INSUFFICIENT_PERMISSIONS.getCode(),
                    ReactiveWebError.INSUFFICIENT_PERMISSIONS.getMessage(), Instant.now());
            final var bytes = this.objectMapper.writeValueAsBytes(errorResponse);

            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return exchange.getResponse().writeWith(
                    Mono.just(exchange.getResponse().bufferFactory().wrap(bytes))
            );
        } catch (JsonProcessingException ex) {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
        }
    }
}
