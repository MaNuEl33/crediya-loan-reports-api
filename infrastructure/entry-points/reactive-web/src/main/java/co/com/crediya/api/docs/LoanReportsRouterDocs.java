package co.com.crediya.api.docs;

import co.com.crediya.api.dtos.AcceptedLoansReportDto;
import co.com.crediya.api.dtos.ErrorResponseDto;
import co.com.crediya.api.handlers.GetAcceptedLoansReportHandler;
import co.com.crediya.api.routers.LoanReportsRouterRest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Tag(name = "loan-reports")
public class LoanReportsRouterDocs {

    @Bean
    @RouterOperations(
            @RouterOperation(
                    path = "/reportes",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            summary = "Accepted Loans Report.",
                            description = "Get the Accepted Loans Report",
                            tags = "loan-reports",
                            operationId = "get-accepted-loans-report",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "OK",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = AcceptedLoansReportDto.class),
                                                    examples = {
                                                            @ExampleObject(
                                                                    name = "Accepted Loans Report successfully retrieved",
                                                                    value = """
                                                                                {
                                                                                    "loan_count": {
                                                                                        "value": 50,
                                                                                        "updated_at": "2025-09-21T20:34:25Z"
                                                                                    },
                                                                                    "loans_total_amount": {
                                                                                        "value": 2500.50,
                                                                                        "updated_at": "2025-09-21T20:34:25Z"
                                                                                    }
                                                                                }
                                                                            """
                                                            )
                                                    }
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "Unauthorized",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                                    examples = {
                                                            @ExampleObject(
                                                                    name = "Invalid Credentials",
                                                                    value = """
                                                                        {
                                                                           "code": "RW_002",
                                                                           "message": "Invalid token.",
                                                                           "timestamp": "2025-09-21T20:34:25Z"
                                                                         }
                                                                        """
                                                            )
                                                    }
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                                    examples = {
                                                            @ExampleObject(
                                                                    name = "Insufficient Permissions",
                                                                    value = """
                                                                        {
                                                                            "code": "RW_003",
                                                                            "message": "You do not have sufficient permissions.",
                                                                            "timestamp": "2025-09-21T20:34:25Z"
                                                                        }
                                                                        """
                                                            )
                                                    }
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal Server Error",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                                    examples = {
                                                            @ExampleObject(
                                                                    name = "Unexpected Error",
                                                                    value = """
                                                                        {
                                                                            "code": "RW_001",
                                                                            "message": "An unexpected error has occurred. Please try again in a moment.",
                                                                            "timestamp": "2025-09-21T20:34:25Z"
                                                                        }
                                                                        """
                                                            )
                                                    }
                                            )
                                    )
                            }
                    )
            )
    )
    public RouterFunction<ServerResponse> getAcceptedLoansReportDoc(
            LoanReportsRouterRest router, GetAcceptedLoansReportHandler getAcceptedLoansReportHandler) {
        return router.loanReportsRoutes();
    }
}
