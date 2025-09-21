package co.com.crediya.sqs.listener;

import co.com.crediya.usecase.updateacceptedloansreport.UpdateAcceptedLoansReportUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.math.BigDecimal;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class SQSProcessor implements Function<Message, Mono<Void>> {

    private final UpdateAcceptedLoansReportUseCase updateAcceptedLoansReportUseCase;

    @Override
    public Mono<Void> apply(Message message) {
        log.info("An accepted loan application has been received.");

        final var bodyMessage = message.body();

        final var objectMapper = new ObjectMapper();
        final JsonNode root;
        final BigDecimal amount;

        try {
            root = objectMapper.readTree(bodyMessage);
            amount = objectMapper.treeToValue(root.get("amount"), BigDecimal.class);
        } catch (JsonProcessingException e) {
            log.error("Error deserializing message", e);
            return Mono.error(e);
        }

        return this.updateAcceptedLoansReportUseCase.execute(amount)
                .then()
                .doOnSuccess(v -> log.info("The accepted loans report has been updated successfully."))
                .doOnError(err -> log.error("Error updating the accepted loans report.", err));
    }
}
